#!/usr/bin/env bash

# 更新代码
git pull

# 发布
mvn clean install -Dgpg.skip

#清理
mvn clean