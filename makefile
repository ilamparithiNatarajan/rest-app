
COMMIT=$$(git rev-parse --short HEAD)
IMAGE_TAG:=$(shell [[ "${REST_APP_LOCAL}" == "1" ]] && echo "local" || echo "$(git rev-parse --short HEAD)")

build:
	docker build -t ilampa/rest-app:local .

start:
	docker-compose up

stop:
	docker-compose down

tag:
	docker tag ilampa/rest-app:local ilampa/rest-app:$(COMMIT)

push:
	docker push ilampa/rest-app:$(COMMIT)

deploy:
	kubectl run backend --image=ilampa/rest-app:$(COMMIT) --port=8080
	kubectl expose deployment.apps/backend --name=api-endpoint --type=LoadBalancer  --port 8080 --target-port=8080