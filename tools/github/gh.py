import os
import re
import requests
import json

# Configuration defaults
GITHUB_TOKEN = os.getenv("GITHUB_TOKEN")
REPO = os.getenv("GITHUB_REPOSITORY")
PROJECT_ID = None

def setup_interactive_config():
    """Interactive flow to create github.json if missing."""
    print("--- GitHub Sync Configuration ---")
    print("Configuration file 'github.json' not found.")
    print("Please run this script from your project root.")
    
    repo = ""
    while not repo:
        repo = input("Enter GitHub repository (e.g., owner/repo): ").strip()
    
    project_id = ""
    while not project_id:
        project_id = input("Enter Project ID for env var prefix (e.g., SHOWCASE): ").strip().upper()
    
    config = {
        "repository": repo,
        "project_id": project_id,
        "ac_prefix": "AC",
        "sync": {
            "files": ["docs/SPEC.md", "docs/GAPS.md"]
        }
    }
    
    with open("github.json", "w") as f:
        json.dump(config, f, indent=2)
    print(f"Created 'github.json'. You can now set your token using:")
    print(f"export {project_id}_GITHUB_TOKEN=\"your_token_here\"")
    return config

# Load or Create github.json
if not os.path.exists("github.json"):
    config = setup_interactive_config()
else:
    try:
        with open("github.json", "r") as f:
            config = json.load(f)
    except Exception as e:
        print(f"Error loading github.json: {e}")
        exit(1)

REPO = REPO or config.get("repository")
PROJECT_ID = config.get("project_id")

# Resolve project-specific token if PROJECT_ID is set
if PROJECT_ID:
    specific_token_key = f"{PROJECT_ID}_GITHUB_TOKEN"
    GITHUB_TOKEN = os.getenv(specific_token_key) or GITHUB_TOKEN

SYNC_ENABLED = bool(GITHUB_TOKEN and REPO)

if not SYNC_ENABLED:
    print(f"Note: GitHub Token or Repository not configured. Issue synchronization is disabled.")

DRY_RUN = os.getenv("DRY_RUN", "false").lower() == "true"
if DRY_RUN:
    print("--- RUNNING IN DRY RUN MODE ---")

def update_github_issue(issue_number, state=None, labels=None):
    """Updates an existing GitHub issue."""
    if DRY_RUN:
        print(f"[DRY RUN] Would update issue #{issue_number} with state={state}, labels={labels}")
        return True

    if not GITHUB_TOKEN or not REPO:
        return False

    url = f"https://api.github.com/repos/{REPO}/issues/{issue_number}"
    headers = {
        "Authorization": f"Bearer {GITHUB_TOKEN}",
        "Accept": "application/vnd.github.v3+json"
    }
    payload = {}
    if state:
        payload["state"] = state
    if labels:
        payload["labels"] = labels
    
    try:
        response = requests.patch(url, headers=headers, json=payload)
        return response.status_code == 200
    except Exception as e:
        print(f"Error updating issue #{issue_number}: {e}")
        return False

def create_github_issue(title, body, labels=None):
    """Creates a GitHub issue and returns the issue number."""
    if DRY_RUN:
        print(f"[DRY RUN] Would create issue: {title}")
        return "MOCK_ID"

    if not GITHUB_TOKEN or not REPO:
        print("Skipping issue creation: GITHUB_TOKEN or GITHUB_REPOSITORY not set.")
        return None

    url = f"https://api.github.com/repos/{REPO}/issues"
    headers = {
        "Authorization": f"Bearer {GITHUB_TOKEN}",
        "Accept": "application/vnd.github.v3+json"
    }
    payload = {
        "title": title,
        "body": body,
        "labels": labels or ["documentation-sync"]
    }
    
    try:
        response = requests.post(url, headers=headers, json=payload)
        if response.status_code == 201:
            return response.json().get("number")
        else:
            print(f"Failed to create issue: {response.status_code} - {response.text}")
            return None
    except Exception as e:
        print(f"Error connecting to GitHub: {e}")
        return None

def sync_markdown_file(filepath):
    """Parses a markdown file for #NEW issue markers and creates GitHub issues."""
    if not os.path.exists(filepath):
        return

    # Determine labels based on file
    default_labels = ["documentation-sync"]
    if "SPEC.md" in filepath:
        default_labels.append("acceptance-criteria")
    elif "GAPS.md" in filepath:
        default_labels.append("technical-debt")

    with open(filepath, "r") as f:
        lines = f.readlines()

    modified = False
    new_lines = []
    
    current_title = None
    current_status = None
    current_block = []
    
    for line in lines:
        if line.startswith("### "):
            current_title = line.replace("### ", "").strip()
            current_status = None
            current_block = [line]
            new_lines.append(line)
        elif "Status" in line and "[" in line:
            if "[x] DONE" in line:
                current_status = "closed"
            else:
                current_status = "open"
            current_block.append(line)
            new_lines.append(line)
        elif "Issue" in line and "#" in line and current_title:
            issue_match = re.search(r"Issue.*#(\d+)", line)
            if "#NEW" in line:
                if SYNC_ENABLED:
                    print(f"Syncing new issue: {current_title}")
                    body = f"Sync source: {filepath}\n\n" + "".join(current_block)
                    issue_number = create_github_issue(current_title, body, labels=default_labels)
                    if issue_number:
                        new_lines.append(line.replace("#NEW", f"#{issue_number}"))
                        modified = True
                    else:
                        new_lines.append(line)
                else:
                    new_lines.append(line)
            elif issue_match and current_status:
                if SYNC_ENABLED:
                    issue_number = issue_match.group(1)
                    print(f"Updating issue #{issue_number} status to {current_status} for: {current_title}")
                    update_github_issue(issue_number, state=current_status)
                new_lines.append(line)
            else:
                new_lines.append(line)
        else:
            if current_title:
                current_block.append(line)
            new_lines.append(line)

    if modified and not DRY_RUN:
        with open(filepath, "w") as f:
            f.writelines(new_lines)
        print(f"Updated {filepath} with new issue IDs.")

if __name__ == "__main__":
    files = ["docs/SPEC.md", "docs/GAPS.md"]
    
    # Load files from config if available
    if os.path.exists("github.json"):
        try:
            with open("github.json", "r") as f:
                config = json.load(f)
                files = config.get("sync", {}).get("files", files)
        except:
            pass

    for f in files:
        sync_markdown_file(f)
