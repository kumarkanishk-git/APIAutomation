package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestBody {
        private String name;
        private Data data;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Data getData() {
                return data;
        }

        public void setData(Data data) {
                this.data = data;
        }
}
