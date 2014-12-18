package App;

import spark.ResponseTransformer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new GsonBuilder()
    	.setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    	.create();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }
}