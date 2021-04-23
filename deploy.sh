#!/usr/bin/env bash

# shellcheck disable=SC2155
export GPG_TTY=$(tty)

# 更新代码
git pull

# 发布
mvn clean -pl .,szm-sofa-boot-starter,szm-sofa-boot-starter-orm deploy -P release

#清理
mvn clean