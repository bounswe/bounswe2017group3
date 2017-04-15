#!/usr/bin/env bash

jmx_port=${JMX_PORT:-"1098"}
rmi_port=${RMI_PORT:-"1099"}
ip_addr="$(ip -4 a show eth0 | grep inet | sed -E -e 's/.*inet //g' | sed -E -e 's/\/[0-9]+.*//g')"

[ -z "$SSH_CLIENT" ] && curl -sSL ${TUNNELS_URL} | bash

export JAVA_TOOL_OPTIONS="${JAVA_TOOL_OPTIONS}\
   -Dcom.sun.management.jmxremote\
   -Dcom.sun.management.jmxremote.port=${jmx_port}\
   -Dcom.sun.management.jmxremote.rmi.port=${rmi_port}\
   -Dcom.sun.management.jmxremote.ssl=false\
   -Dcom.sun.management.jmxremote.authenticate=false\
   -Dcom.sun.management.jmxremote.local.only=true\
   -Djava.rmi.server.hostname=${ip_addr}\
   -Djava.rmi.server.port=${rmi_port}"
