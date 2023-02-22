## Build docker image
```docker build -t <container-name> .```

## Run container
The following cocommand will run the docker container. Please choice an available port.

```docker run  -p <your-port>:8888 <container-name>```
Please copy the generated token from the console. It will be used later.
## Play with the notebook
Go to ```http://localhost:your-port/``` and past the token.
Now you are free to play with the jupyter notebook.
