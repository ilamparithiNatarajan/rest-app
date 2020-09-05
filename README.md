# rest-app ![Java CI](https://github.com/ilamparithiNatarajan/rest-app/workflows/Java%20CI/badge.svg) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ilam-natarajan_rest-app&metric=alert_status)](https://sonarcloud.io/dashboard?id=ilam-natarajan_rest-app)
Backend rest services

swagger: http://localhost:8080/swagger-ui/index.html#

redis:
    redis is used as datastore.
    
    $ brew install redis
    
    $ redis-server
    
    # to flush all keys in redis
    $ redis-cli FLUSHALL