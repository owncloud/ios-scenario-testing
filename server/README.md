## Backend setup

In order to run the tests correctly, an instance of the server must be reachable. You can use an existing instance or create a new one via docker (recommended).

### 1. oC10

Script `populate_oC10_server.sh` will create some users that are defined in the feature files (`Alice`, `Bob`, `Charles`) as well as a group called `test`.

Script requires three parameters

- `host`: hostname or IP where the server runs.
- `port`: port where server is listening.
- `version`: ownCloud version to create the container. Use [one of the available tags](https://hub.docker.com/r/owncloud/server/tags). 

For example:

```
./populate_oC10_server.sh 192.168.1.10 8500 10.12.1
```

```
./populate_oC10_server.sh myserver.com 12000 latest
```


### 2. oCIS

Edit the file `docker-compose.yml`:

- `image`: oCIS version to create container. Use [one of the available tags](https://hub.docker.com/r/owncloud/ocis/tags). 
- `OCIS_URL`: set the URL where the server will listen.

Variable `PROXY_ENABLE_BASIC_AUTH` must be `true`. Otherwise, authentication will not be posible through test execution, and all tests will fail.

Port `9200` must not be already allocated.

The following docker command will create a container with the oCIS instance. It must be executed in the current folder:

```
docker-compose -f docker-compose.yml up
```

Script `populate_oCIS_server.sh` will create some users that are defined in the feature files (`Alice`, `Bob`, `Charles`) as well as a group called `test`.

Server URL is a mandatory parameter for the script:

For example:

```
./populate_oCIS_server.sh https://192.168.1.10:9200
```

```
./populate_oCIS_server.sh https://myserver.com:9200
```

That script is not completed yet, and some actions require manual intervention in admin dashboard to complete the correct setup to execute the test:

- Set user `Alice` as admin
- Add user `Bob` to `test` group

