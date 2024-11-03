#!/bin/bash

# Check if a name was provided
if [ $# -eq 0 ]; then
    echo "No migration name provided."
    exit 1
fi

# Get the current timestamp for unique versioning
VERSION=$(date +%s)
MIGRATION_NAME=$1

# Create the migration file
FILENAME="src/main/resources/db/migration/V${VERSION}__${MIGRATION_NAME}.sql"
touch "$FILENAME"

echo "Migration file created: $FILENAME"