#!/usr/bin/env bash

# shellcheck disable=SC2155
export GPG_TTY=$(tty)

# 更新代码
git pull

# 发布
mvn clean deploy -P release

#清理
mvn clean