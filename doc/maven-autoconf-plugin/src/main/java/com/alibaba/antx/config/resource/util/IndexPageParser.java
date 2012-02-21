package com.alibaba.antx.config.resource.util;

import java.util.List;

import com.alibaba.antx.config.resource.Resource;

public interface IndexPageParser {
    List parse(Resource resource);

    class Item {
        private final String  name;
        private final boolean directory;

        public Item(String name, boolean directory) {
            this.name = name;
            this.directory = directory;
        }

        public String getName() {
            return name;
        }

        public boolean isDirectory() {
            return directory;
        }

        public String toString() {
            return directory ? " [DIR] " + name : "[FILE] " + name;
        }
    }
}
