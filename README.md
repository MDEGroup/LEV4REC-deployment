# LEV4REC-deployment
This repository contains the Docker configuration files for the deployment of Scava platform
The source code and development support is available at [https://github.com/MDEGroup/LEV4REC-Tool/](https://github.com/MDEGroup/LEV4REC-Tool)
You should follow the following steps to run this setup:

1. (Re)Build the docker images using: 

	`docker-compose build`
2. Run the LEV4REC plaftorm using: 

	`docker-compose up` 
	
Please notice that the last command can take more than 5 minutes. It needs to dowload all the dependecies.

1. Access to the LEV4Rec web app by using the following address in the web browser: 
[http://localhost:8891/lev4rec/](http://localhost:8891/lev4rec/)

## Use cases
We use LEV4REC to design, tune, and deploy two existing recommender systems:

1. a k-nearest neighbor-based algorithm (named KNN hereafter) that aims to address the scalability problem in personalized recommendations (a guidated tour is available at [http://localhost:8891/lev4rec/knn](http://localhost:8891/lev4rec/knn)) and
2. AURORA, a feed-forward neural network trained with a curated labeled dataset (a guided tour is available at [http://localhost:8891/lev4rec/ml](http://localhost:8891/lev4rec/ml)).

We make available the output of k-nearest neighbor-based algorithm in the [output_sample](https://github.com/MDEGroup/LEV4REC-deployment/tree/master/output_sample). A detailed guide on how to run the generated artifacts is available in each supported presentation layer, i.e., [evaluation by python script](https://github.com/MDEGroup/LEV4REC-deployment/tree/master/output_sample/evaluation), [docker container with jupyter notebook](https://github.com/MDEGroup/LEV4REC-deployment/tree/master/output_sample/notebook), and a [web servises by flask](https://github.com/MDEGroup/LEV4REC-deployment/tree/master/output_sample/services).



## Docker-compose

In case you want to make sure to start from a fresh installation, please execute the following command:

```
docker system prune -a --volumes
```
