//package com.ithome.cach;
//
//import com.ithome.dto.IndexTagDTO;
//import com.ithome.dto.TagDTO;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.StringJoiner;
//import java.util.stream.Collectors;
//
//public class IndexCache {
//
//
//    public static List<IndexTagDTO> get() {
//
//        List<IndexTagDTO> indexTagDTOs = new ArrayList<>();
//
//        TagDTO program = new TagDTO();
//        program.setCategoryName("前端");
//        program.setTags(Arrays.asList("javascript", "html", "html5", "node.js", "react.js", "jquery", "css3", "type/script", "chrome", "bootstrap", "sass", "less", "chrome-devtools", "firefox", "angular", "coffeescript", "safari", "postman", "edge"));
//        tagDTOS.add(program);
//
//        IndexTagDTO lastnew=new IndexTagDTO();
//        lastnew.setCategoryName("最新");
//        lastnew.setPagInationDTO();
//        indexTagDTOs.add(lastnew);
//        return lastnew;
//
//    }
//
//    /**
//     * 判断标签   不通过的有一个加一个显示
//     *
//     * @param tags
//     * @return
//     */
//    public static String filterInvalid(String tags) {
//        String[] split = tags.split(",");
//        List<TagDTO> tagDTOS = get();
//        //循环所有
//        List<String> tagList = tagDTOS.stream().flatMap(tagDTO -> tagDTO.getTags().stream()).collect(Collectors.toList());
//        StringJoiner joiner = new StringJoiner(",");
//        for (String t : split) {
//            if (!tagList.contains(t)) {
//                joiner.add(t);
//            }
//        }
//        String invalid = joiner.toString();
//        return invalid;
//
//    }
//
//}
//
//
//
