import java.util.ArrayList;
import java.util.List;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {
    private ItemRepository itemRepository;
    private SalesPromotionRepository salesPromotionRepository;

    public App(ItemRepository itemRepository, SalesPromotionRepository salesPromotionRepository) {
        this.itemRepository = itemRepository;
        this.salesPromotionRepository = salesPromotionRepository;
    }

    public String bestCharge(List<String> inputs) {
        String[][] inputsArr = new String[inputs.size()][2];
        List<String> halfList = new ArrayList<String>();
        List<Item> items = itemRepository.findAll();
        List<SalesPromotion> SalesPromotions = salesPromotionRepository.findAll();

        for (int i=0;i<inputs.size();i++){
            inputsArr[i] = inputs.get(i).split(" x ");
        }

        String result = "============= Order details =============\n";
        int total = 0;
        boolean flag = false;
        int save = 0;
        for (int i=0;i<inputs.size();i++){
            for (Item item : items) {
                if(inputsArr[i][0].equals(item.getId())){
                    int cost = (int)item.getPrice()*Integer.parseInt(inputsArr[i][1]);
                    result += item.getName() + " x " + inputsArr[i][1] + " = " + cost + " yuan\n";
                    total += cost;
                    if(SalesPromotions.get(1).getRelatedItems().get(0) == item.getId()||SalesPromotions.get(1).getRelatedItems().get(1) == item.getId()){
                        save += cost/2;
                        halfList.add(item.getName());
                        flag = true;
                    }
                }
            }
        }

        result += "-----------------------------------\n";

        if(flag && total/5 < save){
            result += "Promotion used:\n" + SalesPromotions.get(1).getDisplayName() +
                    " (" + String.join(", ", halfList) +
//                    halfList.toString().substring(1,halfList.toString().length()-1) +
                    ")，saving " + save + " yuan\n" +
                    "-----------------------------------\n";
            total -= save;

        } else if(total>=30){
            int mul = total/5;
            total -= mul;
            result += "Promotion used:\n" +
                    "满30减6 yuan，saving "+ mul + " yuan\n" +
                    "-----------------------------------\n";
        }

        result += "Total：" + total +" yuan\n" +
                "===================================";
        
        return result;
    }
}
