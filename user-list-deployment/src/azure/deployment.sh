#!/bin/bash

acrResourceGroup="${ENV_AZURE_ACR_RESOURCE_GROUP}"
acrName="${ENV_AZURE_ACR_NAME}"
resourceGroup="${ENV_AZURE_RESOURCE_GROUP}"
aksName="${ENV_AZURE_AKS_NAME}"

deployAll() {
  az --version
}

login() {
  az login --service-principal --username "${ENV_AZURE_SP_APP_ID}" --password "${ENV_AZURE_SP_PASSWORD}" \
           --tenant "${ENV_AZURE_SP_TENANT_ID}"
}

createAcr() {
  az group create --name "${acrResourceGroup}" --location "westeurope"
  az acr create --resource-group "${acrResourceGroup}" --location "westeurope" --name "${acrName}" --sku Basic
}

createUserListResources() {
  az group create --name "${resourceGroup}" --location "westeurope"
  az ad sp create-for-rbac --skip-assignment --name "${aksName}"
  az aks create \
      --resource-group "${resourceGroup}" --location "westeurope" \
      --name "${aksName}" \
      --service-principal "${aksName}" \
      --ssh-key-value ~/.ssh/id_rsa.pub \
      --node-count 2 \
      --node-vm-size "Standard_A2_v2" \
      --attach-acr "${acrName}"
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
  createUserListResources)
    login
    createAcr
    createUserListResources
    logout
    ;;
  *)
    echo "Invalid command"
    exit 1
    ;;
esac

