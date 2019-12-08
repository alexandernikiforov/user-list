#!/bin/bash

acrResourceGroup="${ENV_AZURE_ACR_RESOURCE_GROUP}"
acrName="${ENV_AZURE_ACR_NAME}"

deployAll() {
  az --version
}

login() {
  az login --service-principal --username "${ENV_AZURE_SP_APP_ID}" --password "${ENV_AZURE_SP_PASSWORD}" \
           --tenant "${ENV_AZURE_SP_TENANT_ID}"
}

createAcr() {
  az group create --name "${acrResourceGroup}" --location "westeurope"
  az acr create --resource-group "${acrResourceGroup}" --name "${acrName}" --sku Basic --location "westeurope"
}

logout() {
  az logout
}

set -e

case $1 in
  deployAll)
    login
    deployAll
    logout
    ;;
  createAcr)
    login
    createAcr
    logout
    ;;
  *)
    echo "Invalid command"
    exit 1
    ;;
esac

