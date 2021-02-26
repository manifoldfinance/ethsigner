#!/bin/bash
max_memory() {
  # High number which is the max limit until which memory is supposed to be
  # unbounded.
  local mem_file="/sys/fs/cgroup/memory/memory.limit_in_bytes"
  if [ -r "${mem_file}" ]; then
    local max_mem_cgroup="$(cat ${mem_file})"
    local max_mem_meminfo_kb="$(cat /proc/meminfo | awk '/MemTotal/ {print $2}')"
    local max_mem_meminfo="$(expr "$max_mem_meminfo_kb" \* 1024)"
    if [ "${max_mem_cgroup:-0}" != -1 ] && [ "${max_mem_cgroup:-0}" -lt "${max_mem_meminfo:-0}" ]
    then
      echo "${max_mem_cgroup}"
    fi
  fi
}
