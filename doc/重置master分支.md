重置master分支：
git checkout --orphan latest_branch2
git add -A
git commit -am "commit message"
git branch -D master
git branch -m master


git branch -m latest_branch master
git push -f origin master 
git push origin latest_branch2:master -f
git reset --hard origin/master