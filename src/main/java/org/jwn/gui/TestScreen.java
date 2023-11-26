package org.jwn.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TestScreen extends JFrame {
    public TestScreen() {
        setTitle("500x300 프레임 만들기"); // 프레임 제목 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 이 코드는 오른쪽 상단의 종료버튼이 클릭될때 프로그램을 종료시킵니다

        setSize(500, 300); // 프레임 크기 설정
        Container contentPane = getContentPane(); // 프레임에서 컨텐트팬 받아오기
        contentPane.setLayout(null);    // AbsoluteLayout 설정
        // null값 설정으로 알 수 있듯이 AbsoluteLayout은 배치관리자가 없는 컨테이너를 말합니다.

        JLabel lblNewLabel = new JLabel("레이블 테스트 입니다");
        lblNewLabel.setBounds(182, 133, 124, 15); // 레이블 위치 설정
        contentPane.add(lblNewLabel); // 콘텐트팬에 레이블 붙이기

        JButton btnNewButton = new JButton("버튼 테스트");
        btnNewButton.setBounds(182, 59, 110, 23);
        contentPane.add(btnNewButton);

        // 버튼에 ActionListener 등록
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked!");
            }
        });

        // JCheckBox 생성
        JCheckBox checkBox = new JCheckBox("Check Me");
        checkBox.setBounds(182, 10, 110, 23);

        // 체크박스에 ItemListener 등록
        checkBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("Checkbox checked!");
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    System.out.println("Checkbox unchecked!");
                }
            }
        });

        contentPane.add(checkBox);

        JTextField textField = new JTextField();
        textField.setBounds(190, 203, 96, 21);
        contentPane.add(textField);
        textField.setColumns(10); // 텍스트 필드 기본 입력문자 갯수

        // 컨테이너를 화면 중앙에 배치
        setLocationRelativeTo(null);

        setVisible(true); //화면에 프레임 출력
        System.out.println("프레임 출력 완료");
    }
}
