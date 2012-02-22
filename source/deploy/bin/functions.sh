#!/bin/sh
# public functions, created by caosir

# How to use this functions?   . `dirname $0`/functions.sh

# description:  pass/fail color setup, this bit is ripped wholesale from redhat's init scripts :-)
# usage:  [success|failed|warn] $what_you_want_to_say
# author:  caosir
# date:  2003-04-22

if [ -f /etc/sysconfig/init ]; then
    . /etc/sysconfig/init
else
  SETCOLOR_SUCCESS=
  SETCOLOR_FAILURE=
  SETCOLOR_WARNING=
  SETCOLOR_NORMAL=
fi

success () {
    if [ "$BOOTUP" = "color" ]; then
        $SETCOLOR_SUCCESS
        if [ -z "$*" ]; then
            echo "ok"
        else
            echo -e "$*"
        fi
        $SETCOLOR_NORMAL
    else
        if [ -z "$*" ]; then
            echo "ok"
        else
            echo -e "$*"
        fi
    fi
    return
}

failed () {
    if [ "$BOOTUP" = "color" ]; then
        $SETCOLOR_FAILURE
        if [ -z "$*" ]; then
            echo "failed"
        else
            echo -e "$*"
        fi
        $SETCOLOR_NORMAL
    else
        if [ -z "$*" ]; then
            echo "failed"
        else
            echo -e "$*"
        fi
    fi
    return 1
}

warning () {
    if [ "$BOOTUP" = "color" ]; then
        $SETCOLOR_WARNING
        if [ -z "$*" ]; then
            echo "warning"
        else
            echo -e "$*"
        fi
        $SETCOLOR_NORMAL
    else
        if [ -z "$*" ]; then
            echo "warning"
        else
            echo -e "$*"
        fi
    fi
    return
}

