package com.lanji.mylibrary.model;

import java.util.List;

public class EventModel {
    public String eventName;
    public List<EventType>  eventType;
    public static class EventType{
        public String typeName;
        public List<EventParams> eventParams;
    }

    public static class EventParams{
        public String paramsKey;
        public String paramsValue;
    }
}
