
# Let's use the image base-notebook to build our image on top of it
FROM jupyter/base-notebook

COPY . .
RUN python coll_example.py
RUN rm -f coll_example.py
#Let's define this parameter to install jupyter lab instead of the default juyter notebook command so we don't have to use it when running the container with the option -e
ENV JUPYTER_ENABLE_LAB=yes


