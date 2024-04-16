public class Restaurant {
        private String name;
        private String style;
        private double rating;
        public Restaurant(String name, String style, double rating){
            this.name=name;
            this.style=style;
            this.rating=rating;
        }
        public Restaurant(){
            name = "";
            style = "";
            rating = 0.0;
        }
        public String getName(){
            return name;
        }
        public String getStyle(){
            return  style;
        }
        public double getRating(){
            return rating;
        }

}
