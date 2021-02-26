FROM openjdk:11.0.8-jre-slim-buster

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'
#RUN export LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'

ENV OTEL_RESOURCE_ATTRIBUTES="service.name=ethsigner,service.version=$VERSION"

COPY ethsigner /opt/ethsigner/
WORKDIR /opt/ethsigner

# Expose services ports
# 8545 HTTP JSON-RPC
EXPOSE 8545

ENTRYPOINT ["/opt/ethsigner/bin/ethsigner"]

HEALTHCHECK --start-period=5s --interval=5s --timeout=1s --retries=10 CMD bash -c "[ -f /tmp/pid ]"



# Build-time metadata as defined at http://label-schema.org
ARG BUILD_DATE
ARG VCS_REF
ARG VERSION
LABEL org.label-schema.build-date=$BUILD_DATE \
      org.label-schema.name="Ethsigner" \
      org.label-schema.description="Ethereum transaction signing application" \
      org.label-schema.url="https://manifoldfinance.com/ethsigner" \
      org.label-schema.vcs-ref=$VCS_REF \
      org.label-schema.vcs-url="https://github.com/manifoldfinance/ethsigner.git" \
      org.label-schema.vendor="Manifold Finance" \
      org.label-schema.version=$VERSION \
      org.label-schema.schema-version="1.0"
