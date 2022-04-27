#!/bin/bash

set -euo pipefail

./gradlew commons:test data-commons:test moneymanager-ui:test
