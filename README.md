# of-commons

# Internal Memo
FIXME: とりあえず暫定の手動対応の手順。変数用いて共通スクリプト化したほうがよさげ。こなれるまでは gradle task 等にはしないで手動で走らせましょう。世の中の汎用的な手順があるなら取り入れましょう。要調査。
## How to Release
- Move to develop branch.
- Remove `-SNAPSHOT` from `Versions.ofCommonsRelease`
- Execute `./gradlew clean publishMavenPublicationToMavenRepository`
- Commit with message "update maven repository [X.X.X]" and push.
- Copy `mvn-repo` directory into `gh-pages` branch and commit and push as same as above.
- Merge develop branch to master branch.
- Create `vX.X.X` tag from master branch.
- Move to develop branch.
- Bump version up and add `-SNAPSHOT` to `Versions.ofCommonsRelease`
- Commit with message "update maven repository [X.X.X]" and push.
