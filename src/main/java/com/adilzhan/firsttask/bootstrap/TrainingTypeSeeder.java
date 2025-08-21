//package com.adilzhan.firsttask.bootstrap;
//
//import com.adilzhan.firsttask.dao.hibernateDao.TrainingTypeDao;
//import com.adilzhan.firsttask.model.TrainingType;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.UUID;
//
//@Component
//public class TrainingTypeSeeder implements ApplicationRunner {
//    private final TrainingTypeDao trainingTypeDao;
//
//    public TrainingTypeSeeder(TrainingTypeDao trainingTypeDao) {
//        this.trainingTypeDao = trainingTypeDao;
//    }
//
//    @Override
//    @Transactional
//    public void run(ApplicationArguments args) throws Exception {
//        List<String> codes = List.of("YOGA","CARDIO","STRENGTH","CROSSFIT","PILATES","BODYBUILDING","FUNCTIONAL");
//
//        for (String code : codes) {
//            if (trainingTypeDao.findByCode(code).isEmpty()) {
//                trainingTypeDao.save(new TrainingType(
//                        UUID.randomUUID().toString(),
//                        code,
//                        switch (code) {
//                            case "YOGA" -> "Yoga";
//                            case "CARDIO" -> "Cardio";
//                            case "STRENGTH" -> "Strength";
//                            case "CROSSFIT" -> "Crossfit";
//                            case "PILATES" -> "Pilates";
//                            case "BODYBUILDING" -> "Bodybuilding";
//                            case "FUNCTIONAL" -> "Functional";
//                            default -> code;
//                        }
//                ));
//            }
//        }
//    }
//}
