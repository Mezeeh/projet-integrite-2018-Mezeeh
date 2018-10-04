#!/bin/sh
base="filmotheque"
usager="postgres"
motDePasse="sudoroot"
PGPASSWORD=$motDePasse psql --host=localhost --dbname=$base --username=$usager << EOF
SELECT surveillance();
EOF
