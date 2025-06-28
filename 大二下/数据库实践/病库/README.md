1. # 疾病管理系统项目结构分析文档

   ## 项目概述

   本项目是一个基于Java Swing的疾病管理系统，主要用于管理过敏性疾病（如哮喘、过敏性鼻炎等）的临床数据、生物样本、流调数据和随访数据。系统采用经典的三层架构模式（Model-DAO-Service），使用MySQL作为数据库。

   ## 技术栈

   - **开发语言**: Java
   - **UI框架**: Java Swing
   - **数据库**: MySQL 
   - **数据库连接**: JDBC (mysql-connector-j-9.3.0.jar)
   - **架构模式**: MVC + 三层架构 (Model-DAO-Service)

   ## 项目目录结构

   ```
   DiseaseManager/
   ├── src/
   │   ├── ADMS/                          # 主要UI界面包
   │   │   ├── ADMSDemo.java              # 系统演示主类
   │   │   ├── ADMSFrame.java             # 主界面框架
   │   │   ├── DatabaseManager.java       # 数据库管理工具类
   │   │   ├── PatientManageUI.java       # 患者管理界面
   │   │   ├── ClinicalVisitManageUI.java # 临床访问管理界面
   │   │   ├── BioSampleManageUI.java     # 生物样本管理界面
   │   │   ├── FollowUpManageUI.java      # 随访管理界面
   │   │   ├── AddBioSampleDialog.java    # 添加生物样本对话框
   │   │   └── AddFollowUpVisitDialog.java # 添加随访记录对话框
   │   ├── com/szz/                       # 核心业务包
   │   │   ├── dao/                       # 数据访问层
   │   │   ├── service/                   # 业务逻辑层
   │   │   ├── model/                     # 数据模型层
   │   │   ├── view/                      # 视图层
   │   │   ├── util/                      # 工具类
   │   │   ├── test/                      # 测试类
   │   │   └── config.properties          # 配置文件
   │   ├── Main.java                      # 程序入口
   │   └── test/                          # 测试包
   ├── sql/                               # SQL脚本
   │   └── survey_database_tables.sql     # 流调数据库表结构
   ├── lib/                               # 依赖库
   │   └── mysql-connector-j-9.3.0.jar   # MySQL驱动
   └── bin/                               # 编译输出目录
   ```

   ## 四大数据库模块分析

   ### 1. 临床数据库 (Clinical Database)

   #### 核心表结构

   - **patients** - 患者基本信息表
   - **clinical_visits** - 临床访问记录表
   - **visit_symptoms_signs** - 访问症状体征表
   - **diagnoses** - 诊断表
   - **medications** - 用药表
   - **lab_examinations** - 实验室检查表
   - **imaging_studies** - 影像学检查表
   - **pulmonary_function_tests** - 肺功能检查表
   - **exhaled_nitric_oxide_tests** - 呼出一氧化氮检查表

   #### 相关DAO类

   - `PatientDao.java` - 患者数据访问
   - `ClinicalVisitDao.java` - 临床访问数据访问
   - `DiagnosisDao.java` - 诊断数据访问
   - `MedicationDao.java` - 用药数据访问
   - `LabExaminationDao.java` - 实验室检查数据访问
   - `ImagingStudyDao.java` - 影像学检查数据访问
   - `PulmonaryFunctionTestDao.java` - 肺功能检查数据访问
   - `ExhaledNitricOxideTestDao.java` - 呼出一氧化氮检查数据访问
   - `VisitSymptomsSignDao.java` - 症状体征数据访问

   #### 相关Service类

   - `PatientService.java` - 患者业务逻辑
   - `ClinicalVisitService.java` - 临床访问业务逻辑
   - `DiagnosisService.java` - 诊断业务逻辑
   - `MedicationService.java` - 用药业务逻辑
   - `LabExaminationService.java` - 实验室检查业务逻辑
   - `ImagingStudyService.java` - 影像学检查业务逻辑
   - `PulmonaryFunctionTestService.java` - 肺功能检查业务逻辑
   - `ExhaledNitricOxideTestService.java` - 呼出一氧化氮检查业务逻辑

   #### 相关Model类

   - `Patient.java` - 患者实体类
   - `ClinicalVisit.java` - 临床访问实体类
   - `Diagnosis.java` - 诊断实体类
   - `Medication.java` - 用药实体类
   - `LabExamination.java` - 实验室检查实体类
   - `ImagingStudy.java` - 影像学检查实体类
   - `PulmonaryFunctionTest.java` - 肺功能检查实体类
   - `ExhaledNitricOxideTest.java` - 呼出一氧化氮检查实体类
   - `VisitSymptomsSign.java` - 症状体征实体类

   ### 2. 生物样本库 (Biosample Database)

   #### 核心表结构

   - **biosamples** - 生物样本基本信息表
   - **genomic_data** - 基因组数据表
   - **proteomic_data** - 蛋白质组数据表
   - **metabolomic_data** - 代谢组数据表
   - **microbiome_data** - 微生物组数据表

   #### 主要功能

   - 生物样本采集信息管理
   - 样本存储条件记录
   - 多组学数据关联存储
   - 样本质量控制信息

   #### 相关UI界面

   - `BioSampleManageUI.java` - 生物样本管理主界面
   - `AddBioSampleDialog.java` - 添加生物样本对话框

   #### 数据库操作方法 (DatabaseManager.java)

   - `addBioSample()` - 添加生物样本
   - `updateBioSample()` - 更新生物样本信息
   - `getGenomicData()` - 获取基因组数据
   - `getProteomicData()` - 获取蛋白质组数据
   - `getMetabolomicData()` - 获取代谢组数据
   - `getMicrobiomeData()` - 获取微生物组数据

   ### 3. 流调数据库 (Survey Database)

   #### 核心表结构

   - **survey_participants** - 流调参与者表
   - **family_history_survey** - 家族史调查表
   - **home_environment_exposure** - 家庭环境暴露表
   - **work_study_environment_exposure** - 工作学习环境暴露表
   - **urban_rural_environment_monitoring** - 城乡环境监测表
   - **potential_confounding_factors** - 潜在混杂因素表
   - **environmental_monitoring_methods_survey** - 环境监测方法调查表

   #### 相关DAO类

   - `SurveyParticipantDao.java` - 流调参与者数据访问
   - `FamilyHistorySurveyDao.java` - 家族史调查数据访问
   - `HomeEnvironmentExposureDao.java` - 家庭环境暴露数据访问
   - `WorkStudyEnvironmentExposureDao.java` - 工作学习环境暴露数据访问
   - `UrbanRuralEnvironmentMonitoringDao.java` - 城乡环境监测数据访问
   - `PotentialConfoundingFactorsDao.java` - 潜在混杂因素数据访问
   - `EnvironmentalMonitoringMethodsSurveyDao.java` - 环境监测方法调查数据访问
   - `SurveyInvestigatorDao.java` - 调查员数据访问

   #### 相关Service类

   - `SurveyParticipantService.java` - 流调参与者业务逻辑
   - `FamilyHistorySurveyService.java` - 家族史调查业务逻辑
   - `HomeEnvironmentExposureService.java` - 家庭环境暴露业务逻辑
   - `WorkStudyEnvironmentExposureService.java` - 工作学习环境暴露业务逻辑
   - `UrbanRuralEnvironmentMonitoringService.java` - 城乡环境监测业务逻辑
   - `PotentialConfoundingFactorsService.java` - 潜在混杂因素业务逻辑
   - `EnvironmentalMonitoringMethodsSurveyService.java` - 环境监测方法调查业务逻辑
   - `SurveyInvestigatorService.java` - 调查员业务逻辑

   #### 相关Model类

   - `SurveyParticipant.java` - 流调参与者实体类
   - `FamilyHistorySurvey.java` - 家族史调查实体类
   - `HomeEnvironmentExposure.java` - 家庭环境暴露实体类
   - `WorkStudyEnvironmentExposure.java` - 工作学习环境暴露实体类
   - `UrbanRuralEnvironmentMonitoring.java` - 城乡环境监测实体类
   - `PotentialConfoundingFactors.java` - 潜在混杂因素实体类
   - `EnvironmentalMonitoringMethodsSurvey.java` - 环境监测方法调查实体类
   - `SurveyInvestigator.java` - 调查员实体类

   ### 4. 随访数据库 (Follow-up Database)

   #### 核心表结构

   - **followup_visits** - 随访访问记录表
   - **followup_symptoms** - 随访症状表
   - **followup_signs** - 随访体征表
   - **followup_diagnoses** - 随访诊断表
   - **followup_medications** - 随访用药表
   - **followup_questionnaires** - 随访问卷表
   - **followup_lab_tests** - 随访实验室检查表
   - **followup_pulmonary_function_tests** - 随访肺功能检查表
   - **followup_feno_tests** - 随访FeNO检查表
   - **followup_nasoendoscopy** - 随访鼻内镜检查表
   - **followup_hearing_tests** - 随访听力检查表
   - **followup_nasal_resistance_tests** - 随访鼻阻力检查表
   - **followup_imaging_studies** - 随访影像学检查表
   - **followup_past_medication_history** - 随访既往用药史表
   - **followup_other_treatments** - 随访其他治疗表
   - **followup_summary_info** - 随访总结信息表
   - **followup_costs** - 随访费用表
   - **followup_adverse_drug_reactions** - 随访药物不良反应表

   #### 相关UI界面

   - `FollowUpManageUI.java` - 随访管理主界面
   - `AddFollowUpVisitDialog.java` - 添加随访记录对话框

   #### 数据库操作方法 (DatabaseManager.java)

   - `addFollowUpVisit()` - 添加随访记录
   - `updateFollowUpVisit()` - 更新随访记录
   - `getFollowUpSymptoms()` - 获取随访症状
   - `getFollowUpSigns()` - 获取随访体征
   - `getFollowUpDiagnoses()` - 获取随访诊断
   - `getFollowUpMedications()` - 获取随访用药
   - `getFollowUpQuestionnaires()` - 获取随访问卷
   - `getFollowUpLabTests()` - 获取随访实验室检查
   - `getFollowUpPulmonaryFunctionTests()` - 获取随访肺功能检查
   - `getFollowUpFeNOTests()` - 获取随访FeNO检查
   - `getFollowUpNasoendoscopy()` - 获取随访鼻内镜检查
   - `getFollowUpHearingTests()` - 获取随访听力检查
   - `getFollowUpNasalResistanceTests()` - 获取随访鼻阻力检查
   - `getFollowUpImagingStudies()` - 获取随访影像学检查
   - `getFollowUpPastMedicationHistory()` - 获取随访既往用药史
   - `getFollowUpOtherTreatments()` - 获取随访其他治疗
   - `getFollowUpSummaryInfo()` - 获取随访总结信息
   - `getFollowUpCosts()` - 获取随访费用
   - `getFollowUpAdverseDrugReactions()` - 获取随访药物不良反应

   ## 核心工具类

   ### DatabaseManager.java

   - 数据库连接管理
   - 通用数据库操作方法
   - 表格模型构建工具

   ### JDBCUtil.java (推测存在)

   - JDBC连接工具类
   - 数据库配置管理

   ### DatabaseInitializer.java

   - 数据库表结构初始化
   - 流调数据库表创建

   ## 主要UI界面

   ### ADMSFrame.java

   - 系统主界面框架
   - 各模块入口管理

   ### PatientManageUI.java

   - 患者信息管理界面
   - 患者增删改查功能

   ### ClinicalVisitManageUI.java

   - 临床访问管理界面
   - 就诊记录管理

   ### BioSampleManageUI.java

   - 生物样本管理界面
   - 组学数据查询界面

   ### FollowUpManageUI.java

   - 随访数据管理界面
   - 随访记录详情查看

   ## 数据库关系设计

   ### 主要外键关系

   1. **patients** → **clinical_visits** (patient_id)
   2. **patients** → **survey_participants** (patient_id)
   3. **clinical_visits** → **biosamples** (visit_id)
   4. **patients** → **followup_visits** (patient_id)
   5. **survey_participants** → **family_history_survey** (participant_id)
   6. **survey_participants** → **home_environment_exposure** (participant_id)
   7. **biosamples** → **genomic_data/proteomic_data/metabolomic_data/microbiome_data** (biosample_id)

   ### 数据流向

   ```
   患者注册 → 临床访问 → 生物样本采集 → 组学数据分析
       ↓
   流调参与 → 环境暴露调查 → 家族史调查
       ↓
   随访管理 → 长期跟踪 → 疗效评估
   ```

   ### 系统架构图说明

   系统采用经典的分层架构设计：

   1. **用户界面层**: 提供各种管理界面，包括患者管理、临床访问管理、生物样本管理、随访管理和流调管理
   2. **业务逻辑层**: 封装业务规则和流程，处理复杂的业务逻辑
   3. **数据访问层**: 提供统一的数据访问接口，封装SQL操作
   4. **数据模型层**: 定义实体类，映射数据库表结构
   5. **工具类层**: 提供数据库连接、初始化等通用功能
   6. **数据库层**: 四大数据库模块，存储不同类型的业务数据

   ### 数据库关系图说明

   数据库设计以患者(PATIENTS)为核心，建立了完整的数据关联体系：

   1. **患者中心设计**: 所有数据都以患者为中心进行组织
   2. **模块化存储**: 不同类型的数据分别存储在对应的数据库模块中
   3. **关联完整性**: 通过外键约束保证数据的完整性和一致性
   4. **扩展性良好**: 每个模块都可以独立扩展，不影响其他模块

   ## 系统特点

   1. **模块化设计**: 四大数据库模块相对独立，便于维护和扩展
   2. **标准三层架构**: Model-DAO-Service分层清晰，职责明确
   3. **丰富的数据类型**: 支持临床、流调、生物样本、随访等多种数据类型
   4. **完整的CRUD操作**: 每个模块都提供完整的增删改查功能
   5. **用户友好界面**: 基于Swing的图形界面，操作直观
   6. **数据完整性**: 通过外键约束保证数据一致性

   ## 详细类分析

   ### 临床数据库核心类详解

   #### Patient.java (患者实体类)

   ```java
   主要字段:
   - id: 患者ID (主键)
   - hospitalPatientId: 医院患者ID
   - name: 患者姓名
   - gender: 性别
   - dateOfBirth: 出生日期
   - homeAddress: 家庭住址
   - birthWeightKg: 出生体重
   - lifestyleNotes: 生活方式备注
   - positiveFoodAllergenHistory: 食物过敏原阳性史
   - positiveInhaledAllergenHistory: 吸入过敏原阳性史
   - allergicDiseaseHistory: 过敏性疾病史
   - familyAllergyHistoryDegree1: 一级亲属过敏史
   - familyAllergicDiseaseHistoryDegree1: 一级亲属过敏性疾病史
   - familyAllergyHistoryDegree2: 二级亲属过敏史
   - familyAllergicDiseaseHistoryDegree2: 二级亲属过敏性疾病史
   ```

   #### ClinicalVisit.java (临床访问实体类)

   ```java
   主要字段:
   - id: 访问ID (主键)
   - patientId: 患者ID (外键)
   - visitDate: 访问日期
   - heightCm: 身高(厘米)
   - weightKg: 体重(公斤)
   - healthcareProviderName: 医疗提供者姓名
   - healthcareProviderTitle: 医疗提供者职称
   - institutionName: 机构名称
   - institutionAddress: 机构地址
   
   内部类 VisitSymptomSign:
   - 哮喘症状: asthmaWheezing, asthmaCoughing, asthmaDyspnea等
   - 过敏性鼻炎症状: arClearNasalDischarge, arNasalItching等
   - 过敏性鼻炎体征: arNasalMucosaSwelling, arTurbinateHypertrophy等
   ```

   ### 流调数据库核心类详解

   #### SurveyParticipant.java (流调参与者实体类)

   ```java
   主要字段:
   - id: 参与者ID (主键)
   - patientId: 患者ID (外键)
   - surveyIdentifier: 调查编号
   - surveyDate: 调查时间
   - name: 姓名
   - gender: 性别
   - ageAtSurvey: 调查时年龄
   - residenceType: 居住地类型
   - residenceDurationYears: 居住时长
   - homeAddressSurvey: 家庭住址
   - heightCmSurvey: 身高
   - weightKgSurvey: 体重
   
   既往过敏史字段:
   - hasPreviousAllergyHistory: 既往过敏史
   - previousAllergicRhinitis: 既往过敏性鼻炎
   - previousBronchialAsthma: 既往支气管哮喘
   - previousAtopicDermatitis: 既往特应性皮炎
   - previousAllergicConjunctivitis: 既往过敏性结膜炎
   - previousUrticaria: 既往荨麻疹
   
   本次调查诊断字段:
   - currentSurveyDiagAr: 本次调查诊断过敏性鼻炎
   - currentSurveyDiagBa: 本次调查诊断支气管哮喘
   - currentSurveyDiagAd: 本次调查诊断特应性皮炎
   - currentSurveyDiagAc: 本次调查诊断过敏性结膜炎
   - currentSurveyDiagUrticaria: 本次调查诊断荨麻疹
   ```

   #### FamilyHistorySurvey.java (家族史调查实体类)

   ```java
   主要字段:
   - id: 记录ID (主键)
   - participantId: 参与者ID (外键)
   - relationshipToParticipant: 与参与者关系
   - familyMemberName: 家族成员姓名
   - gender: 性别
   - ageAtDiagnosis: 诊断时年龄
   - medicalCondition: 疾病状况
   - diagnosisDate: 诊断日期
   - currentStatus: 当前状态
   - treatmentHistory: 治疗史
   - medicationHistory: 用药史
   - allergyHistory: 过敏史
   - smokingHistory: 吸烟史
   - drinkingHistory: 饮酒史
   - occupationalExposure: 职业暴露
   - environmentalFactors: 环境因素
   - geneticTestingResults: 基因检测结果
   ```

   #### HomeEnvironmentExposure.java (家庭环境暴露实体类)

   ```java
   主要字段:
   - id: 记录ID (主键)
   - participantId: 参与者ID (外键)
   - housingType: 住房类型
   - buildingAge: 建筑年龄
   - heatingType: 供暖类型
   - cookingFuelType: 烹饪燃料类型
   - hasAirConditioning: 有空调
   - hasVentilationSystem: 有通风系统
   - humidityLevel: 湿度水平
   - temperatureControl: 温度控制
   - hasPets: 有宠物
   - petTypes: 宠物类型
   - hasIndoorPlants: 有室内植物
   - plantTypes: 植物类型
   - hasSmokingIndoors: 室内吸烟
   - smokingFrequency: 吸烟频率
   - hasChemicalExposure: 化学物质暴露
   - chemicalTypes: 化学物质类型
   - hasMoldOrDampness: 霉菌或潮湿
   - moldLocation: 霉菌位置
   - hasDustMites: 尘螨
   - dustMiteLocation: 尘螨位置
   - hasCarpets: 有地毯
   - carpetType: 地毯类型
   - cleaningFrequency: 清洁频率
   - cleaningProducts: 清洁产品
   - hasAirPurifier: 有空气净化器
   - airPurifierType: 空气净化器类型
   - waterSource: 水源
   - waterQuality: 水质
   - noiseLevel: 噪音水平
   - lightingConditions: 照明条件
   - nearbyPollutionSources: 附近污染源
   - neighborhoodEnvironment: 社区环境
   ```

   ### 生物样本库核心功能

   #### 生物样本管理 (BioSampleManageUI.java)

   ```java
   主要功能模块:
   1. 生物样本搜索和管理
      - 按ID、患者ID、访问ID、样本类型、同意书ID搜索
      - 样本信息的增删改查
      - 样本数据排序功能
   
   2. 组学数据查询
      - 基因组数据查询 (genomic_data表)
      - 蛋白质组数据查询 (proteomic_data表)
      - 代谢组数据查询 (metabolomic_data表)
      - 微生物组数据查询 (microbiome_data表)
   
   样本字段包括:
   - patient_id: 患者ID
   - visit_id: 访问ID
   - sample_type: 样本类型
   - collection_datetime: 采集时间
   - collection_site: 采集部位
   - preprocessing_method: 预处理方法
   - storage_temperature_celsius: 存储温度
   - freeze_thaw_cycles: 冻融循环次数
   - storage_duration_days: 存储天数
   - rna_integrity_index: RNA完整性指数
   - dna_concentration_ng_ul: DNA浓度
   - linked_clinical_phenotype_summary: 关联临床表型总结
   - consent_id: 同意书ID
   - lab_processing_notes: 实验室处理备注
   ```

   ### 随访数据库核心功能

   #### 随访管理 (FollowUpManageUI.java)

   ```java
   主要功能模块:
   1. 随访记录管理
      - 随访访问记录的增删改查
      - 按患者ID、医院患者ID搜索
      - 随访记录排序功能
   
   2. 随访详情管理 (多标签页)
      - 症状记录 (followup_symptoms)
      - 体征记录 (followup_signs)
      - 诊断记录 (followup_diagnoses)
      - 用药记录 (followup_medications)
      - 问卷记录 (followup_questionnaires)
      - 实验室检查 (followup_lab_tests)
      - 肺功能检查 (followup_pulmonary_function_tests)
      - FeNO检查 (followup_feno_tests)
      - 鼻内镜检查 (followup_nasoendoscopy)
      - 听力检查 (followup_hearing_tests)
      - 鼻阻力检查 (followup_nasal_resistance_tests)
      - 影像学检查 (followup_imaging_studies)
      - 既往用药史 (followup_past_medication_history)
      - 其他治疗 (followup_other_treatments)
      - 总结信息 (followup_summary_info)
      - 费用记录 (followup_costs)
      - 药物不良反应 (followup_adverse_drug_reactions)
   
   随访访问字段包括:
   - patient_id: 患者ID
   - hospital_patient_id_followup: 医院患者随访ID
   - visit_datetime: 访问时间
   - is_initial_visit: 是否初次访问
   - height_cm: 身高
   - weight_kg: 体重
   - provider_name: 提供者姓名
   - provider_title: 提供者职称
   - home_address: 家庭住址
   ```

   ## DAO层设计模式

   ### 通用DAO操作

   每个DAO类都遵循相似的设计模式:

   ```java
   public class XxxDao {
       // 创建记录
       public int createXxx(Xxx entity) throws Exception
   
       // 根据ID获取记录
       public Xxx getXxxById(int id) throws Exception
   
       // 获取所有记录
       public List<Xxx> getAllXxx() throws Exception
   
       // 更新记录
       public boolean updateXxx(Xxx entity) throws Exception
   
       // 删除记录
       public boolean deleteXxx(int id) throws Exception
   
       // 条件查询
       public List<Xxx> searchXxx(条件参数) throws Exception
   
       // ResultSet映射到实体对象
       private Xxx mapResultSetToXxx(ResultSet rs) throws SQLException
   }
   ```

   ### Service层设计模式

   ```java
   public class XxxService {
       private XxxDao xxxDao;
   
       public XxxService() {
           this.xxxDao = new XxxDao();
       }
   
       // 业务逻辑方法，包装DAO调用并处理异常
       public int createXxx(Xxx entity) {
           try {
               return xxxDao.createXxx(entity);
           } catch (Exception e) {
               // 异常处理和日志记录
               throw new RuntimeException("业务操作失败", e);
           }
       }
   
       // 其他业务方法...
   }
   ```

   ## 数据库连接管理

   ### DatabaseManager.java 核心方法

   ```java
   // 数据库连接获取
   public static Connection getConnection() throws SQLException
   
   // 通用表格模型构建
   public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException
   
   // 参数设置工具方法
   private static void setIntOrNull(PreparedStatement pstmt, int index, String value)
   private static void setStringOrNull(PreparedStatement pstmt, int index, String value)
   private static void setDecimalOrNull(PreparedStatement pstmt, int index, String value)
   ```

   ## 扩展建议

   1. **数据备份机制**: 添加定期数据备份功能
   2. **权限管理**: 实现用户角色和权限控制
   3. **数据导入导出**: 支持Excel等格式的数据导入导出
   4. **统计分析**: 添加数据统计和分析功能
   5. **Web化改造**: 考虑将Swing界面改造为Web界面
   6. **API接口**: 提供RESTful API供其他系统调用
   7. **数据验证**: 加强输入数据的验证和约束
   8. **缓存机制**: 实现数据缓存提高查询性能
   9. **日志系统**: 添加完整的操作日志记录
   10. **国际化支持**: 支持多语言界面

   ## 项目总结

   ### 项目优势

   1. **完整的业务覆盖**: 系统涵盖了疾病管理的完整流程，从患者登记、临床访问、生物样本采集、流调数据收集到长期随访，形成了完整的数据闭环。

   2. **标准化的架构设计**: 采用经典的三层架构模式，代码结构清晰，职责分离明确，便于维护和扩展。

   3. **丰富的数据模型**: 针对过敏性疾病的特点，设计了详细的数据模型，包括症状体征、环境暴露、家族史等多维度信息。

   4. **用户友好的界面**: 基于Java Swing的图形界面，提供了直观的操作体验，支持多标签页和表格操作。

   5. **数据完整性保证**: 通过外键约束和事务处理，确保数据的一致性和完整性。

   ### 技术特点

   1. **模块化设计**: 四大数据库模块相对独立，每个模块都有完整的MVC结构。

   2. **统一的数据访问**: 通过DatabaseManager类提供统一的数据库操作接口。

   3. **灵活的查询功能**: 支持多条件组合查询和排序功能。

   4. **组学数据支持**: 专门设计了生物样本库模块，支持基因组、蛋白质组、代谢组、微生物组等多组学数据管理。

   ### 应用价值

   1. **科研支持**: 为过敏性疾病的临床研究提供了完整的数据管理平台。

   2. **临床应用**: 支持医院和诊所的日常患者管理和随访工作。

   3. **流行病学调查**: 提供了专业的流调数据收集和管理功能。

   4. **数据标准化**: 建立了过敏性疾病数据管理的标准化模板。

   ### 发展前景

   该系统为过敏性疾病管理提供了一个坚实的基础平台，随着功能的不断完善和技术的升级，有望成为该领域的标准化解决方案。特别是在精准医学和个性化治疗日益重要的今天，系统的多组学数据管理能力将发挥重要作用。

   ---

   **文档版本**: 1.0
   **创建日期**: 2024年
   **最后更新**: 2024年
   **文档作者**: 项目分析团队
