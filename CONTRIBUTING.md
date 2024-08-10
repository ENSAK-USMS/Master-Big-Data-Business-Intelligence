# Collaboration Guidelines

Thank you for your interest in contributing to our project! To ensure effective collaboration, please follow these guidelines:

1. [Forking the Repository](#forking-the-repository)
2. [Adding the Remote Branch](#adding-the-remote-branch)
3. [Submitting an Issue](#submitting-an-issue)
4. [Submitting a Pull Request](#submitting-a-pull-request)
5. [Handling Pull Request changes](#handling-pull-request-changes)
6. [Resolving merge conflicts](#resolving-merge-conflicts)
7. [After your pull request is merged](#after-your-pull-request-is-merged)
8. [Addtionnal Resources](#addtionnal-resources)



### Forking the Repository

1. Navigate to the [main repository](https://github.com/ENSAK-USMS/Master-Big-Data-Business-Intelligence).

2. Click on the "Fork" button in the upper right corner of the GitHub interface. This will create a copy of the repository in your GitHub account.

3. Clone your forked repository to your local machine:

    ```shell
    git clone https://github.com/Your-Username/Your-Fork-Name.git
    ```

4. Change into the project's directory:

    ```shell
    cd Your-Fork-Name
    ```

### Adding the Remote Branch

1. Add the upstream repository as a remote to keep your fork updated:

    ```shell
    git remote add upstream https://github.com/ENSAK-USMS/Master-Big-Data-Business-Intelligence.git
    ```

2. Verify that the upstream remote is added successfully:

    ```shell
    git remote -v
    ```

   This should show both `origin` (your fork) and `upstream` (the main repository).

3. Create a new branch for your changes:

    ```shell
    git checkout -b my-fix-branch main
    ```

   Replace `my-fix-branch` with an appropriate name for your branch.

Now, you can make your changes, commit them, and follow the rest of the contribution guidelines.






### Submitting an Issue

Before you submit your issue search the [archive](https://github.com/ENSAK-USMS/Master-Big-Data-Business-Intelligence/issues?utf8=%E2%9C%93&q=is%3Aissue), maybe your question was already answered or you may find some related content.


- **Issue Overview**: Check if a similar issue exists. Provide a clear description of the problem or feature request.
- **Reproduce the Error**: If reporting a bug, provide steps to reproduce it.
- **Related Issues**: Mention any related issues or pull requests.
- **Suggest a Fix**: If possible, propose a solution for the issue.

### Submitting a Pull Request

Before you submit your pull request, consider the following guidelines:

- Search [GitHub](https://github.com/ENSAK-USMS/Master-Big-Data-Business-Intelligence/pulls?utf8=%E2%9C%93&q=is%3Apr) for an open or closed Pull Request
  that relates to your submission.
- If you want to modify the project code structure, please start a discussion about it first
- Make your changes in a new git branch

  ```shell
  git checkout -b my-fix-branch main
  ```

- Create your patch, **including appropriate test cases**, Note: if you aren't able to create tests, consider adding **need tests** label 
- Commit your changes using a descriptive commit message.

- Push your branch to GitHub:

  ```shell
  git push origin my-fix-branch
  ```

  - In GitHub, send a pull request to `ENSAK-USMS/Master-Big-Data-Business-Intelligence:main`.
  - if your pr includes multiple tasks and you're not done yet, consider creating a draft pull request with a task list to allow other members to track the issue's progress 
### Handling Pull Request changes
   If we suggest changes, then
  - Make the required updates.
  - Make sure the tests are still passing
  - Rebase your branch and force push to your GitHub repository (this will update your Pull Request):

    ```shell
    git rebase main -i
    git push -f
    ```
That's it! Thank you for your contribution!

### Resolving merge conflicts 
("This branch has conflicts that must be resolved")

Sometimes your PR will have merge conflicts with the upstream repository's main branch. There are several ways to solve this but if not done correctly this can end up as a true nightmare. So here is one method that works quite well.

- First, fetch the latest information from the main

  ```shell
  git fetch upstream
  ```

- Rebase your branch against the upstream/main

  ```shell
  git rebase upstream/main
  ```

- Git will stop rebasing at the first merge conflict and indicate which file is in conflict. Edit the file, resolve the conflict then 

  ```shell
  git add <the file that was in conflict>
  git rebase --continue
  ```
- The rebase will continue up to the next conflict. Repeat the previous step until all files are merged and the rebase ends successfully.
- Force push to your GitHub repository (this will update your Pull Request)

  ```shell
  git push -f
  ```



### After your pull request is merged

After your pull request is merged, you can safely delete your branch and pull the changes
from the main (upstream) repository:

- Delete the remote branch on GitHub either through the GitHub web UI or your local shell as follows:

  ```shell
  git push origin --delete my-fix-branch
  ```

- Check out the main branch:

  ```shell
  git checkout main -f
  ```

- Delete the local branch:

  ```shell
  git branch -D my-fix-branch
  ```

- Update your main with the latest upstream version:

  ```shell
  git pull --ff upstream main
  ```
### Addtionnal Resources
1. [Adding A linked folder to your project](#adding-a-linked-folder-to-your-project)


#### Adding A linked folder to your project
#### How to Use Git Submodule to Add a Linked Folder to Another Project

Git submodule is a powerful feature in Git that allows you to include a separate Git repository as a subdirectory within your own repository. This can be useful when you want to add a linked folder from another project to your own project. In the context of students working on different projects, using Git submodule can help them easily incorporate shared code or resources from a central repository.

Here's a step-by-step guide on how to use Git submodule to add a linked folder to another project:

1. Navigate to the root directory of your project where you want to add the linked folder.

2. Open a terminal or command prompt in the root directory.

3. Add the external repository as a submodule using the following command:
  ```shell
  git submodule add <repository_url> <submodule_path>
  ```
  Replace `<repository_url>` with the URL of the external repository and `<submodule_path>` with the desired path where you want the linked folder to be added within your project.

4. Commit the changes to your main project repository:
  ```shell
  git commit -m "Added submodule: <submodule_path>"
  ```

5. Push the changes to your remote repository:
  ```shell
  git push origin main
  ```

6. When other students clone your project repository, they need to initialize and update the submodule. Instruct them to run the following commands:
  ```shell
  git submodule init
  git submodule update
  ```

7. The linked folder from the external repository will now be available within your project. Students can work with the shared code or resources as if they were part of their own project.

8. To update the linked folder to the latest version from the external repository, navigate to the submodule directory and run the following commands:
  ```shell
  cd <submodule_path>
  git pull origin main
  ```

  Note: Students should be cautious when updating the submodule, as it may introduce changes that could potentially break their project.

That's it! You have successfully added a linked folder from another project using Git submodule. This allows students to collaborate and share code or resources easily while maintaining separate project repositories.

For more information on Git submodules, refer to the official Git documentation: [Git Submodules](https://git-scm.com/book/en/v2/Git-Tools-Submodules)

