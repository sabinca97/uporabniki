# RSO: Image metadata microservice

## Prerequisites
- tako poženemo lokalno podatkovno bazo
```bash
docker run -d --name pg-image-metadata -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=image-metadata -p 65432:5432 postgres:13
```
### CI / CD

Status Travic CI-ja :https://travis-ci.com/github/sabinca97/mojRepozitorij

- Za continous integration uporabljen Travis CI/CD

- Ko naredimo push v repozitorij se:
    - avtomatsko poženejo ukazi iz datoteke ```.travis.yml```
    - ukazi zgradijo projekt (```Dockerfile```)
    - sliko pushajo na Docker Hub 
    
### Dockerfile
- Dockerfile iz zbrane kode zgradi jar datoteko in jo shrani, nato zgradi sliko kot docker image.
    - login v dockerhub z ukazom ```docker login```
    - ukaz ```mvn clean package```
    - gradnjo sliko zaženemo z ukazom ```docker build -t sabinca97/novaslika:1 .```
    - push slike v dockerhub ```docker push sabinca97/novaslika:1```

### Kubernetes
- Microsoft Azur "mojRso"
- splošni ukazi
   - kreiranje  ```kubectl create -f uporabniki-deployment.yaml```
   - posodabljanje ```kubectl apply -f uporabniki-deployment.yaml```
   - tukaj je zapisan external ip za dostop ```kubectl get services```
   - pridobim pode ```kubectl get pods```
   - pridobim deployments ```kubectl get deployments```
   - pridobim pode ```kubectl get pods```
- brisanje 
   - izbris deploymenta  ```kubectl delete deploy/uporabniki-deployment```
   - izbris servica  ```kubectl delete service/uporabniki```
   
- za posodobitev
    - naprej odstranim imagePullAllways
    - posodobitev  ```kubectl apply -f uporabniki-deployment.yaml```
    - dam nazaj imagePullAllways
    - posodobitev  ```kubectl apply -f uporabniki-deployment.yaml```
    
- aktiven k8s url
    - ```http://40.76.163.134:8080/v1/images```
    
### Consul
 - dostop na  ```127.0.0.1:8500```
 - ce je tukaj false, potem gre prek, ce dam na true jo blokira```http://127.0.0.1:8500/ui/dc1/kv/environments/dev/services/uporabniki-service/1.0.0/config/rest-properties/maintenance-mode/edit```
 
    
### Health Check
- kličem get ```http://localhost:8080/health/live```
- v classu DemoResource spreminjam broken true/false



