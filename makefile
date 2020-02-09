
COMMIT=$$(git rev-parse --short HEAD)


build:
	docker build -t ilampa/rest-app:local .

start:
	docker-compose up

tag:
	docker tag ilampa/rest-app:local ilampa/rest-app:$(COMMIT)

push:
	docker push ilampa/rest-app:$(COMMIT)