## tfs-proxy

tfs-proxy allows you to access information of your on-premise Team Foundation Server via simple web requests.

### Why?

Currently it was created as side-project for [node-build-monitor](https://github.com/marcells/node-build-monitor). Microsoft had released a REST API for its Visual Studio Online Services. But for the on-premise Team Foundation Server there is no easy way to query the build information.

### How does it work?

tfs-proxy is a standalone Java console application running the [Spark web framework](http://sparkjava.com/). On a request 
the [Microsoft Visual Studio Team Foundation Server 2012 SDK for Java](https://www.microsoft.com/en-us/download/details.aspx?id=22616) is used to access the information of the Team Foundation Server.

#### How to use it?

1. Run tfs-proxy via Docker (see Run with Docker below) or build it with maven (see Dockerfile for the needed build commands)
2. Make a web request to `http://localhost:4567/builds` with the following headers:
    - `url`, eg. `http://tfs-server:8080/tfs/DefaultCollection`
    - `username`: eg. `domain\buildadmin`
    - `password`: eg. `buildadmin_secret`
3. Check the JSON response for the queried build details

For a quick check if tfs-proxy is running correctly you can use `curl`
```
curl --header "url: http://tfs-server:8080/tfs/DefaultCollection" --header "username: domain\buildadmin" --header "password: buildadmin_secret" http://localhost:4567/builds
```

### Run with Docker

You could build the tfs-proxy docker image by your-self, if you want to.

```bash
docker build -t tfs-proxy .
docker run -d -p 4567:4567 tfs-proxy
```

You can also use the pre-built docker image [marcells/tfs-proxy](https://registry.hub.docker.com/u/marcells/tfs-proxy/) in the Docker Hub.

```bash
docker run -d -p 4567:4567 marcells/tfs-proxy
```