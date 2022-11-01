# Funcoast H&I Attendance Populator

In case the data for the app gets wiped out (again lol), here is a tool for restoring it. 

## Setup

The postgres credentials should be available on the development machine as `POSTGRES_ROOT_USER` and `POSTGRES_ROOT_PASSWORD` environment variables respectively.

It assumes that a CSV in the same format as the standard attendance sheet is in the root of the `src/main/resources` directory.

It requires OAuth2 configuration for calling the deployed `funcoast-hi-server` application. The configuration should be in `oauth2.yml` in the root of this project and needs the following format:

```yaml
oauth2:
  funcoast-hi:
    client-key: ####
    client-secret: ####
  user:
    name: ####
    password: ####
```

Lastly, in the `application.yml`, update important information for the program to run:

```yaml
funcoast:
  attendance:
    # yyyy-MM-dd, must be first day of month for calculations to work
    first-meeting-date: #####
```

## What It Does

This application will populate the bare minimum required to get the application up and running again.