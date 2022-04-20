#!/bin/bash

set -euo pipefail

./gradlew commons:assemble data-commons:assemble moneymanager-ui:assemble
