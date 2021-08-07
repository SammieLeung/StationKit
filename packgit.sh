#!/bin/bash

set -e

tar -cf git.tar .git .gitignore
rm -rf .git
rm .gitignore
