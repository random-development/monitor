# Monitor

## Docker

The build process is performed within the container in its entirety which means
that no external dependencies are needed. 

    $ git clone https://github.com/random-development/monitor
    $ cd monitor
    $ docker build .

Using `docker-compose` is recommended. The image exposes the web server on port
`8080`.
