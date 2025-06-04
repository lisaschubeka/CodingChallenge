package com.example.codingchallenge.ui
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.codingchallenge.HL7ViewModel

@Composable
fun HL7Screen(viewModel: HL7ViewModel = viewModel()) {
    val user by viewModel.user.collectAsState()
    val testResults by viewModel.testResults.collectAsState()

    var input by remember { mutableStateOf("MSH|^~\\&|PROMED-OPEN|IMD Berlin MVZ|MCS|TESTX|20221123103004||ORU^R01|2211231030040555890287|P|2.3|||AL|AL|DE||DE|\n" +
            "PID|1||||Test^Jeannette HL7||19800101|O|||Test, Jeannette HL7^^^^^^^||||||||\n" +
            "PV1|1||||||Medizinisches Labor Potsdam^^^^^^DE||||||||||||||E|||E\n" +
            "ORC|NW||0555890287^IMD Berlin MVZ||A||||20221123103004||||||20221123095200|||||||||||||||||||||||||\n" +
            "OBR|1||0555890287^IMD Berlin MVZ||APN|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|1|ST|6APN^Alkalische Phosphatase i.S.||!!Storno|µmol/s*l|||||D|||||Schreiber, Jeannette\n" +
            "OBR|2||0555890287^IMD Berlin MVZ||APN|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|2|CE|7AP^Alkalische Phosphatase i.S.(Photom.)||1.09|µmol/s*l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|3||0555890287^IMD Berlin MVZ||BILI|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|3|CE|7BILI^Bilirubin (gesamt) i.S.    (Photom.)||8.54|µmol/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|4||0555890287^IMD Berlin MVZ||CA|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|4|ST|6CA^Calcium i.S.||!!Storno|mmol/l|||||D|||||Schreiber, Jeannette\n" +
            "OBR|5||0555890287^IMD Berlin MVZ||CA|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|5|CE|7CA^Calcium i.S.               (Photom.)||2.29|mmol/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|6||0555890287^IMD Berlin MVZ||CL|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|6|ST|6CL^Chlorid i.S.                   (ISE)||!!Storno|mmol/l|98 - 106||||D|||||Schreiber, Jeannette\n" +
            "OBR|7||0555890287^IMD Berlin MVZ||CL|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|7|CE|7CL^Chlorid i.S.                   (ISE)||106|mmol/l|98 - 107||||P|||||Schreiber, Jeannette\n" +
            "OBR|8||0555890287^IMD Berlin MVZ||CHOL|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|8|ST|6CHOL^Cholesterin i.S.||!!Storno|mmol/l|||||D|||||Schreiber, Jeannette\n" +
            "NTE|1||          \n" +
            "NTE|2||Bei TG-Werten > 4.56 mmol/l(> 400 mg/dl) erfolgt die\n" +
            "NTE|3||Berechnung von Non-HDL-Cholesterin.\n" +
            "OBR|9||0555890287^IMD Berlin MVZ||CHOL|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|9|CE|7CHOL^Cholesterin i.S.           (Photom.)||5.60|mmol/l|||||P|||||Schreiber, Jeannette\n" +
            "NTE|4||          \n" +
            "NTE|5||Bei TG-Werten > 4.56 mmol/l(> 400 mg/dl) erfolgt die\n" +
            "NTE|6||Berechnung von Non-HDL-Cholesterin.\n" +
            "OBR|10||0555890287^IMD Berlin MVZ||HDL|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|10|ST|6HDL^HDL-Cholesterin i.S.||!!Storno|mmol/l|||||D|||||Schreiber, Jeannette\n" +
            "OBR|11||0555890287^IMD Berlin MVZ||HDL|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|11|CE|7HDL^HDL-Cholesterin i.S.       (Photom.)||1.40|mmol/l|||||P|||||Schreiber, Jeannette\n" +
            "NTE|7||          \n" +
            "NTE|8||hoher Risikofaktor für eine Herzerkrankung:\n" +
            "NTE|9||< 1.04 mmol/l  (< 40 mg/dl)\n" +
            "OBR|12||0555890287^IMD Berlin MVZ||LDL|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|12|ST|6LDL^LDL-Cholesterin i.S.||!!Storno|mmol/l|||||D|||||Schreiber, Jeannette\n" +
            "NTE|10||          \n" +
            "NTE|11||Zielwerte:\n" +
            "NTE|12||< 1.8 mmol/l  bei sehr hohem Risiko\n" +
            "NTE|13||              >= 50% LDL-Reduktion (wenn der Zielwert\n" +
            "NTE|14||              nicht erreicht werden kann)\n" +
            "NTE|15||< 2.6 mmol/l  bei hohem Risiko\n" +
            "NTE|16||< 3.0 mmol/l  bei moderatem/niedrigem Risiko\n" +
            "OBR|13||0555890287^IMD Berlin MVZ||LDL|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|13|CE|7LDL^LDL-Cholesterin i.S.       (Photom.)||3.6|mmol/l|||||P|||||Schreiber, Jeannette\n" +
            "NTE|17||          \n" +
            "NTE|18||Zielwerte:\n" +
            "NTE|19||< 1.8 mmol/l  bei sehr hohem Risiko\n" +
            "NTE|20||              >= 50% LDL-Reduktion (wenn der Zielwert\n" +
            "NTE|21||              nicht erreicht werden kann)\n" +
            "NTE|22||< 2.6 mmol/l  bei hohem Risiko\n" +
            "NTE|23||< 3.0 mmol/l  bei moderatem/niedrigem Risiko\n" +
            "OBR|14||0555890287^IMD Berlin MVZ||FE|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|14|ST|6FE^Eisen i.S.||!!Storno|µmol/l|||||D|||||Schreiber, Jeannette\n" +
            "OBR|15||0555890287^IMD Berlin MVZ||FE|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|15|CE|7FE^Eisen i.S.                 (Photom.)||21.2|µmol/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|16||0555890287^IMD Berlin MVZ||GE|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|16|ST|6GE^Gesamteiweiß i.S.||!!Storno|g/l|||||D|||||Schreiber, Jeannette\n" +
            "OBR|17||0555890287^IMD Berlin MVZ||GE|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|17|CE|7GE^Gesamteiweiß i.S.          (Photom.)||70.9|g/l|64.0 - 83.0||||P|||||Schreiber, Jeannette\n" +
            "OBR|18||0555890287^IMD Berlin MVZ||GGTN|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|18|ST|6GGTN^GGT i.S.||!!Storno|µmol/s*l|||||D|||||Schreiber, Jeannette\n" +
            "OBR|19||0555890287^IMD Berlin MVZ||GGTN|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|19|CE|7GGT^GGT i.S.                   (Photom.)||0.55|µmol/s*l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|20||0555890287^IMD Berlin MVZ||ASATN|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|20|ST|6ASATN^ASAT (GOT) i.S.||!!Storno|µmol/s*l|||||D|||||Schreiber, Jeannette\n" +
            "OBR|21||0555890287^IMD Berlin MVZ||ASATN|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|21|CE|7ASAT^ASAT (GOT) i.S.            (Photom.)||0.35|µmol/s*l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|22||0555890287^IMD Berlin MVZ||ALATN|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|22|ST|6ALATN^ALAT (GPT) i.S.||!!Storno|µmol/s*l|||||D|||||Schreiber, Jeannette\n" +
            "OBR|23||0555890287^IMD Berlin MVZ||ALATN|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|23|CE|7ALAT^ALAT (GPT) i.S.            (Photom.)||0.40|µmol/s*l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|24||0555890287^IMD Berlin MVZ||HS|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|24|ST|6HS^Harnsäure i.S.||!!Storno|µmol/l|||||D|||||Schreiber, Jeannette\n" +
            "NTE|24||          \n" +
            "NTE|25||Therapieziel bei symptomatischer Gicht <6 mg/dl (<357 umol/l).\n" +
            "OBR|25||0555890287^IMD Berlin MVZ||HS|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|25|CE|7HS^Harnsäure i.S.             (Photom.)||269|µmol/l|||||P|||||Schreiber, Jeannette\n" +
            "NTE|26||          \n" +
            "NTE|27||Therapieziel bei symptomatischer Gicht <357 µmol/l\n" +
            "NTE|28||(<6 mg/dl).\n" +
            "OBR|26||0555890287^IMD Berlin MVZ||HBA1|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|26|CE|5HBA1^Hämoglobin A1c i. EDTA-Blut||4.8|%|< 5.7||||P|||||Schreiber, Jeannette\n" +
            "OBR|27||0555890287^IMD Berlin MVZ||HBA1|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|27|CE|5HBA1N^Hämoglobin A1c i. EDTA-Blut||29|mmol/mol|< 39.0||||P|||||Schreiber, Jeannette\n" +
            "NTE|29||          \n" +
            "NTE|30||Screening auf Diabetes mellitus:\n" +
            "NTE|31||\n" +
            "NTE|32||<5.7% (<39 mmol/mol)       Kein Hinweis auf Diabetes mellitus.\n" +
            "NTE|33|| 5.7-6.4% (39-47 mmol/mol) Grenzbereich, weitere Abklärung\n" +
            "NTE|34||                           über Nüchternglukose und evtl.\n" +
            "NTE|35||                           oGTT angezeigt.\n" +
            "NTE|36||>6.4% (>47 mmol/mol)       Wert spricht für Diabetes mellitus.\n" +
            "NTE|37||\n" +
            "NTE|38||Therapieverlaufskontrolle bei D.m.:\n" +
            "NTE|39||Zielbereich 6.5-7.5% (48-58 mmol/mol)\n" +
            "OBR|28||0555890287^IMD Berlin MVZ||IGA|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|28|ST|6IGA^IgA i.S.                     (Turb.)||!!Storno|mg/dl|||||D|||||Schreiber, Jeannette\n" +
            "OBR|29||0555890287^IMD Berlin MVZ||IGA|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|29|CE|7IGA^IgA i.S.                     (Turb.)||1.85|g/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|30||0555890287^IMD Berlin MVZ||IGE|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|30|CE|5IGE^IgE i.S. (FEIA) (3rd WHO)||14.3|kU/l|< 85.0||||P|||||Schreiber, Jeannette\n" +
            "OBR|31||0555890287^IMD Berlin MVZ||IGG|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|31|ST|6IGG^IgG i.S.                     (Turb.)||!!Storno|mg/dl|||||D|||||Schreiber, Jeannette\n" +
            "OBR|32||0555890287^IMD Berlin MVZ||IGG|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|32|CE|7IGG^IgG i.S.                     (Turb.)||14.1|g/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|33||0555890287^IMD Berlin MVZ||IGM|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|33|ST|6IGM^IgM i.S.                     (Turb.)||!!Storno|mg/dl|||||D|||||Schreiber, Jeannette\n" +
            "OBR|34||0555890287^IMD Berlin MVZ||IGM|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|34|CE|7IGM^IgM i.S.                     (Turb.)||1.93|g/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|35||0555890287^IMD Berlin MVZ||K|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|35|ST|6K^Kalium i.S. (ISE)||!!Storno|mmol/l|3.3 - 5.1||||D|||||Schreiber, Jeannette\n" +
            "OBR|36||0555890287^IMD Berlin MVZ||K|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|36|CE|7K^Kalium i.S.                    (ISE)||5.0|mmol/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|37||0555890287^IMD Berlin MVZ||CREA|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|37|ST|6CREA^Kreatinin i.S.||!!Storno|µmol/l|||||D|||||Schreiber, Jeannette\n" +
            "OBR|38||0555890287^IMD Berlin MVZ||CREA|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|38|CE|7CREA^Kreatinin i.S.                (enz.)||83|µmol/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|39||0555890287^IMD Berlin MVZ||LDHN|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|39|ST|6LDHN^LDH i.S.||!!Storno|µmol/s*l|||||D|||||Schreiber, Jeannette\n" +
            "NTE|40||          \n" +
            "NTE|41||Männer   < 4.22\n" +
            "NTE|42||Frauen   < 4.25\n" +
            "OBR|40||0555890287^IMD Berlin MVZ||LDHN|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|40|CE|7LDH^LDH i.S.                   (Photom.)||3.72|µmol/s*l|2.08 - 3.67|++|||P|||||Schreiber, Jeannette\n" +
            "OBR|41||0555890287^IMD Berlin MVZ||LIP|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|41|ST|6LIP^Lipase i.S.||!!Storno|µmol/s*l|< 1.00||||D|||||Schreiber, Jeannette\n" +
            "OBR|42||0555890287^IMD Berlin MVZ||LIP|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|42|CE|7LIP^Lipase i.S.                (Photom.)||0.87|µmol/s*l|< 1.00||||P|||||Schreiber, Jeannette\n" +
            "OBR|43||0555890287^IMD Berlin MVZ||NA|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|43|ST|6NA^Natrium i.S. (ISE)||!!Storno|mmol/l|136 - 145||||D|||||Schreiber, Jeannette\n" +
            "OBR|44||0555890287^IMD Berlin MVZ||NA|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|44|CE|7NA^Natrium i.S.                   (ISE)||142|mmol/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|45||0555890287^IMD Berlin MVZ||PHOS|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|45|ST|6PHOS^Phosphat i.S.||!!Storno|mmol/l|||||D|||||Schreiber, Jeannette\n" +
            "OBR|46||0555890287^IMD Berlin MVZ||PHOS|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|46|CE|7PHOS^Phosphat i.S.              (Photom.)||1.41|mmol/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|47||0555890287^IMD Berlin MVZ||TRAN|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|47|ST|6TRAN^Transferrin i.S. (Turbi.)||!!Storno|mg/dl|||||D|||||Schreiber, Jeannette\n" +
            "OBR|48||0555890287^IMD Berlin MVZ||TRAN|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|48|CE|7TRAN^Transferrin i.S.            (Turbi.)||2.90|g/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|49||0555890287^IMD Berlin MVZ||TRI|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|49|ST|6TRI^Triglyceride i.S.||!!Storno|mmol/l|< 1.7||||D|||||Schreiber, Jeannette\n" +
            "NTE|43||          \n" +
            "NTE|44||Referenzbereich nach 12stündiger Nahrungskarenz\n" +
            "OBR|50||0555890287^IMD Berlin MVZ||TRI|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|50|CE|7TRI^Triglyceride i.S.          (Photom.)||2.22|mmol/l|< 1.70|+|||P|||||Schreiber, Jeannette\n" +
            "NTE|45||          \n" +
            "NTE|46||nach 12stündiger Nahrungskarenz: <1.70 mmol/l (<150 mg/dl)\n" +
            "NTE|47||\n" +
            "NTE|48||grenzwertig hoch: 1.70 - 2.25 mmol/l  (150 - 199 mg/dl)\n" +
            "NTE|49||hoch            : 2.26 - 5.64 mmol/l  (200 - 499 mg/dl)\n" +
            "NTE|50||sehr hoch       :      > 5.65 mmol/l  (    > 500 mg/dl)\n" +
            "OBR|51||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|51|CE|5LEUKO^Leukozyten||6.8|Gpt/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|52||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|52|CE|5ERY^Erythrozyten||5.11|Tpt/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|53||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|53|CE|5HB^Hämoglobin||9.2|mmol/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|54||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|54|CE|5HK^Hämatokrit||0.45|l/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|55||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|55|CE|5MCV^MCV (Hk/Ery-Zahl)||88|fl|||||P|||||Schreiber, Jeannette\n" +
            "OBR|56||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|56|CE|5MCH^MCH (Hb/Ery-Zahl)||1.80|fmol|||||P|||||Schreiber, Jeannette\n" +
            "OBR|57||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|57|CE|5MCHC^MCHC (Hb/Hk)||20.5|mmol/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|58||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|58|CE|5THROM^Thrombozyten||291|Gpt/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|59||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|59|ST|5DIFF^Differentialblutbild (automat.)||.||||||P|||||Schreiber, Jeannette\n" +
            "NTE|51||          \n" +
            "OBR|60||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|60|CE|NEUTRO^ neutrophile Granulozyten||66.8|%|||||P|||||Schreiber, Jeannette\n" +
            "OBR|61||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|61|CE|LYMPH^ Lymphozyten||20.3|%|||||P|||||Schreiber, Jeannette\n" +
            "OBR|62||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|62|CE|MONO^ Monozyten||8.9|%|||||P|||||Schreiber, Jeannette\n" +
            "OBR|63||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|63|CE|EOS^ eosinophile Granulozyten||3.4|%|||||P|||||Schreiber, Jeannette\n" +
            "OBR|64||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|64|CE|BASO^ basophile Granulozyten||0.6|%|||||P|||||Schreiber, Jeannette\n" +
            "OBR|65||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|65|CE|5RDW^RDW-CV:||12.7|%|11.5   - 15.0||||P|||||Schreiber, Jeannette\n" +
            "NTE|52||          \n" +
            "NTE|53||RDW-CV = ERY-Volumenverteilungsbreite\n" +
            "OBR|66||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|66|ST|COM1^Kommentar||!!Storno||||||D|||||Schreiber, Jeannette\n" +
            "OBR|67||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|67|CE|GRANU^ unreife Granulozyten||1.7|%|< 0.6||||P|||||Schreiber, Jeannette\n" +
            "OBR|68||0555890287^IMD Berlin MVZ||BS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|68|ST|NRBC^ Erythroblasten||!!Storno|%|||||D|||||Schreiber, Jeannette\n" +
            "OBR|69||0555890287^IMD Berlin MVZ||DIFABS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|69|ST|5DIABS^Differentialblutbild (absolut)||.||||||P|||||Schreiber, Jeannette\n" +
            "NTE|54||          \n" +
            "OBR|70||0555890287^IMD Berlin MVZ||DIFABS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|70|ST|5NEUTA^ neutrophile Granulozyten||folgt|Gpt/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|71||0555890287^IMD Berlin MVZ||DIFABS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|71|ST|5LYMA^ Lymphozyten||folgt|Gpt/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|72||0555890287^IMD Berlin MVZ||DIFABS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|72|ST|5MONA^ Monozyten||folgt|Gpt/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|73||0555890287^IMD Berlin MVZ||DIFABS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|73|ST|5EOSA^ eosinophile Granulozyten||folgt|Gpt/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|74||0555890287^IMD Berlin MVZ||DIFABS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|74|ST|5BASA^ basophile Granulozyten||folgt|Gpt/l|||||P|||||Schreiber, Jeannette\n" +
            "OBR|75||0555890287^IMD Berlin MVZ||DIFABS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|75|ST|5GRANA^ unreife Granulozyten||folgt|Gpt/l|< 0.06||||P|||||Schreiber, Jeannette\n" +
            "OBR|76||0555890287^IMD Berlin MVZ||DIFABS|||202211230952|||055589028702||LABOR||EBL|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|76|ST|5NRBCA^ Erythroblasten||!!Storno|Gpt/l|||||D|||||Schreiber, Jeannette\n" +
            "OBR|77||0555890287^IMD Berlin MVZ||QUOT1|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|77|ST|6QUOT1^LDL/HDL  Quotient||!!Storno||||||D|||||Schreiber, Jeannette\n" +
            "OBR|78||0555890287^IMD Berlin MVZ||QUOT1|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|78|CE|7QUOT1^LDL/HDL  Quotient||2.57||||||P|||||Schreiber, Jeannette\n" +
            "OBR|79||0555890287^IMD Berlin MVZ||TRANS|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||D||||||||||||||||||||||||0\n" +
            "OBX|79|ST|6TRANS^Transferrin-Sättigung||!!Storno|%|||||D|||||Schreiber, Jeannette\n" +
            "OBR|80||0555890287^IMD Berlin MVZ||TRANS|||202211230952|||055589028701||LABOR||VB|TESTX|||||||||P||||||||||||||||||||||||0\n" +
            "OBX|80|CE|7TRANS^Transferrin-Sättigung||29.1|%|||||P|||||Schreiber, Jeannette\n" +
            "\n") }

    LaunchedEffect(Unit) {
        viewModel.parseHL7Message(input)
    }
    Column() {
        UserHeader(user)
        Column(Modifier.padding(16.dp)) {

            Spacer(Modifier.height(16.dp))
            if (testResults.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    // TODO not a good place to filter, should be done earlier
                    items(testResults.filter { t -> t.range != "" }) { result ->

                        result.value.toFloatOrNull()?.let { numericValue ->


                            TestResultCard(
                                testName = result.testName,
                                value = numericValue,
                                unit = result.unit,
                                range = result.range,
                                note = result.note
                            )
                        }
                    }
                }
            }

        }
    }



}
