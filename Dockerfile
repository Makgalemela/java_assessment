FROM ubuntu:latest
LABEL authors="matome"

ENTRYPOINT ["top", "-b"]
