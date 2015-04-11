package momenify.proconnect.objects;

/**
 * Created by shahbazkhan on 3/5/15.
 */

public class AUserProfile  {
    private String name;
    private String email;
    private String position;
    private String company;
   public AUserProfile()
   {

   }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
       this.email=email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company=company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position=position;
    }

//    public ParseFile getPhotoFile() {
//        return getParseFile("photo");
//    }
//
//    public void setPhotoFile(ParseFile file) {
//        put("photo", file);
//    }
}
