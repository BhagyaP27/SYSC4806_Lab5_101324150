# SYSC 4806 – Lab 5: AddressBook App

![Java CI with Maven](https://github.com/BhagyaP27/SYSC4806_Lab5_101324150/actions/workflows/maven.yml/badge.svg)

**Name:** Bhagya P.
**Student number:** 101324150

**Azure deployment:** https://friendlister-c6a9ahabebbhdwbr.canadacentral-01.azurewebsites.net/addressbook

(Free tier: the first load may take a minute or two.)

## Endpoints
- `POST /api/addressbooks` – create an address book
- `GET /api/addressbooks/{id}` – get an address book
- `POST /api/addressbooks/{id}/buddies` – add a buddy
- `GET /api/addressbooks/{id}/buddies` – list an address book's buddies (new in Lab 5)
- `GET /addressbook?id=1` – HTML view

## CI/CD
GitHub Actions (`.github/workflows/maven.yml`) runs the integration tests and builds the app on every push/PR to `master`. Azure App Service deploys from `master`.
