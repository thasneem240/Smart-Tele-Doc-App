package com.example.capstoneprojectgroup4;

// MedicalHistoryItem.java
    public class MedicalHistoryItem
    {
        private String date;
        private String description;

        public MedicalHistoryItem(String date, String description)
        {
            this.date = date;
            this.description = description;
        }

        public String getDate()
        {
            return date;
        }

        public String getDescription()
        {
            return description;
        }
    }

