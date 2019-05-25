# Monitor [![Build Status](https://travis-ci.org/random-development/monitor.svg?branch=master)](https://travis-ci.org/random-development/monitor)

## Docker

The build process is performed within the container in its entirety which means
that no external dependencies are needed. 

    $ git clone https://github.com/random-development/monitor
    $ cd monitor
    $ docker build .

The image exposes a monitor instance on port `8080`.

While you can user `docker` directly using `docker-compose` is recommended.

## H2

If the `dev` profile is active then an H2 console can be accessed after
navigating to `localhost:8080/h2`.
