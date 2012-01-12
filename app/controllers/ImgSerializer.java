package controllers;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import models.Img;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
class ImgSerializer implements JsonSerializer<Img> {
	public JsonElement serialize(Img img, Type type,
			JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		object.add("caption", new JsonPrimitive(StringUtils.defaultString(img.caption, "")));
		object.add("desc", new JsonPrimitive(StringUtils.defaultString(img.desciption, "")));
		object.add("width", new JsonPrimitive(img.width));
		object.add("height", new JsonPrimitive(img.height));
		object.add("hash", new JsonPrimitive(img.hash));
		object.add("type", new JsonPrimitive(img.type));
		object.add("deleteHash", new JsonPrimitive(img.deleteHash));
		object.add("deleteHash", new JsonPrimitive(img.deleteHash));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssZ");
		String iso = sdf.format(img.createDate);
		object.add("createDate", new JsonPrimitive(iso));
		object.add("viewLink", new JsonPrimitive(img.viewLink()));
		object.add("views", new JsonPrimitive(img.views));
		object.add("votes", new JsonPrimitive(img.votes));
		object.add("orignalLink", new JsonPrimitive(img.orignalLink()));
		object.add("largeLink", new JsonPrimitive(img.largeLink()));
		object.add("smallLink", new JsonPrimitive(img.smallLink()));
		return object;
	}
}