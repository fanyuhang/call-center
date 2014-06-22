package com.common.security.entity;

import com.common.core.annotation.Comment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Allen
 * Date: 10/4/12
 */
@Comment(value = "资源")
public class Resource implements Serializable {

    private String name;

    private String display;

    private List<Column> columns = new ArrayList<Column>();

    public Resource() {
    }

    public Resource(String name, String display) {
        this.name = name;
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public void addColumn(String name, String display, String type) {
        Column column = new Column(name, display, type);
        this.columns.add(column);
    }

    static class Column {

        private String name;

        private String display;

        private String type;

        Column() {
        }

        Column(String name, String display, String type) {
            this.name = name;
            this.display = display;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Column column = (Column) o;

            if (name != null ? !name.equals(column.name) : column.name != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "Column{" +
                    "name='" + name + '\'' +
                    ", display='" + display + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
