# SYSC 4806 – Lab 6: AddressBook App

![Java CI with Maven](https://github.com/BhagyaP27/SYSC4806_Lab5_101324150/actions/workflows/maven.yml/badge.svg)

**Name:** Bhagya Patel

**Student number:** 101324150

**Azure deployment:** https://friendlister-c6a9ahabebbhdwbr.canadacentral-01.azurewebsites.net/

(Free tier: the first load may take a minute or two.)

## Web pages
- `GET /` – list all address books, with a button to create a new one
- `GET /addressbook?id=1` – list one address book's buddies, with a form to add a buddy

## REST endpoints
- `POST /api/addressbooks` – create an address book
- `GET /api/addressbooks/{id}` – get an address book
- `POST /api/addressbooks/{id}/buddies` – add a buddy
- `GET /api/addressbooks/{id}/buddies` – list an address book's buddies (new in Lab 5)

## Spring Data REST
- `GET /data/addressBooks`, `GET /data/buddies` – auto-generated endpoints from the repositories

## CI/CD
GitHub Actions (`.github/workflows/maven.yml`) runs the integration tests and builds the app on every push/PR to `master`. Azure App Service deploys from `master`.