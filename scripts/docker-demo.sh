#!/usr/bin/env bash
set -euo pipefail
project_root=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)
image=${BICIAM_IMAGE:-biciam:practica5}

docker build --tag "$image" "$project_root"
result=$(docker run --rm "$image")
printf '%s\n' "$result"
if [[ "$result" != "BiCIAM | RandomSearch | x=3 | f(x)=0.0" ]]; then
    printf '%s\n' 'La salida de la búsqueda no coincide con el resultado esperado.' >&2
    exit 1
fi
