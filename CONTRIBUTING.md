# Contribution Guide

## Development

- For every new change, branch out of `master`. `master` branch is what is in production.
  - Name your branch with this structure: `topic/name`.
    - Topic is `enhancement`, `fix` or `feature`.
      - Use `feature` for new features, `enhancement` for improving previous features, `fix` for bug fixes.
    - Name is a 4-5 word explanation of your branch.
  - You must use kebab-case for branch names, which is all lowercase with `-` separating the words. Ex: `feature/implementing-events-endpoint`, **not** `feature/implementingEventsEndpoint` or `feature/implementingeventsendpoint`.
- Please follow the language's conventions as much as possible.
- **Don't put everything into one commit, this is very important**. Separate your task into mini subtasks and create a commit for each one of them. 
  - Use [Git Commit Message Styleguide](#git-commit-message-styleguide) for your commit messages.
- You must include thoughtfully-worded, well-structured documentations with your code.
- You must include unit tests with your code for each and every possible case.
- **Create one branch/PR per action, this is also very important**. It can be a feature, a fix, a refactoring, anything, but try to separate concerns and make several branches/PRs for several purposes rather than put everything in one.

## Git Commit Message Styleguide

* Use the present tense ("Add feature" not "Added feature").
* Use the imperative mood ("Move cursor to..." not "Moves cursor to...").
* Limit the first line to 72 characters or less.
* Reference issues and pull requests liberally.
* When only changing documentation, include `[ci skip]` in the commit description.
* Consider starting the commit message with an applicable emoji.
    * :art: `:art:` when improving the format/structure of the code
    * :racehorse: `:racehorse:` when improving performance
    * :non-potable_water: `:non-potable_water:` when plugging memory leaks
    * :memo: `:memo:` when writing docs
    * :penguin: `:penguin:` when fixing something on Linux
    * :apple: `:apple:` when fixing something on macOS
    * :checkered_flag: `:checkered_flag:` when fixing something on Windows
    * :bug: `:bug:` when fixing a bug
    * :fire: `:fire:` when removing code or files
    * :green_heart: `:green_heart:` when fixing the CI build
    * :white_check_mark: `:white_check_mark:` when adding tests
    * :lock: `:lock:` when dealing with security
    * :arrow_up: `:arrow_up:` when upgrading dependencies
    * :arrow_down: `:arrow_down:` when downgrading dependencies
    * :shirt: `:shirt:` when removing linter warnings
    
## Deployment
 
- Before merging your branch to `master`, you must get **at least one approved review** from another person.
- `master` branch is what is in production, and it's automatically deployed to our production environment in Heroku on each merge. You don't have to do anything manual.
