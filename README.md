# LEV4REC-deployment
This repository contains the Docker configuration files for the deployment of Scava platform
The suorce code and development support is available at [https://github.com/MDEGroup/LEV4REC-Tool/](https://github.com/MDEGroup/LEV4REC-Tool)
You should follow the following steps to run this setup:

1. (Re)Build the docker images using: 

	`docker-compose build`
2. Run the LEV4REC plaftorm using: 

	`docker-compose up` 
	
	Please notice that for this setup we are using an empty mongodb database for oss-db
1. Access to the LEV4Rec web app by using the following address in the web browser: 
[http://localhost:8891/lev4rec/](http://localhost:8891/lev4rec/)


## Docker-compose

In case you want to make sure to start from a fresh installation, consider to execute:

```
docker system prune -a --volumes
```
