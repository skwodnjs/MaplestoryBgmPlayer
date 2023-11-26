package org.jwn.gui;

import org.jwn.data.DataReceiver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class BGMPathScreen extends JFrame {
    public BGMPathScreen() {
        setTitle("MAPLESTORY BGM PLAYER"); // 프레임 제목 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 이 코드는 오른쪽 상단의 종료버튼이 클릭될때 프로그램을 종료시킵니다
        setSize(480, 120); // 프레임 크기 설정
        Container contentPane = getContentPane(); // 프레임에서 컨텐트팬 받아오기
        contentPane.setLayout(new BorderLayout());

        // 컴포넌트 추가
        JLabel label = new JLabel("다운로드 경로");
        JTextField pathTF = new JTextField(DataReceiver.root);
        JButton btn = new JButton("선택");
        JCheckBox checkBox = new JCheckBox("하위 디렉토리 추가");
        JButton downloadButton = new JButton("다운로드");

        contentPane.add(label);
        contentPane.add(pathTF);
        contentPane.add(btn);
        contentPane.add(checkBox);
        contentPane.add(downloadButton);
        
        // 컴포넌트 동작 설정
        pathTF.setEnabled(false);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = getFilePath();
                pathTF.setText(path);
            }
        });

        checkBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                subDirectoryStateChanged(e.getStateChange());
            }
        });

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDownloadButtonPressed();
            }
        });

        // 레이아웃
        JPanel north = new JPanel();
        north.setLayout(new BorderLayout());
        north.add(label, BorderLayout.WEST);
        north.add(pathTF, BorderLayout.CENTER);
        north.add(btn, BorderLayout.EAST);

        contentPane.add(north, BorderLayout.NORTH);
        contentPane.add(checkBox, BorderLayout.CENTER);
        contentPane.add(downloadButton, BorderLayout.SOUTH);

        // 화면에 프레임 출력
        setLocationRelativeTo(null);
        setVisible(true);
        System.out.println("프레임 출력 완료");
    }

    private String getFilePath() {
        JFileChooser fileChooser = new JFileChooser(DataReceiver.root);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fileChooser.showOpenDialog(getParent());
        // 창 열기 정상 수행시 0 반환, 취소시 1 반환

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            DataReceiver.root = fileChooser.getSelectedFile().getPath();
        }

        return DataReceiver.root;
    }

    private void subDirectoryStateChanged(int i) {
        if (i == ItemEvent.SELECTED) {
            DataReceiver.makeSubDirectory = true;
        } else if (i == ItemEvent.DESELECTED) {
            DataReceiver.makeSubDirectory = false;
        }
    }

    private void onDownloadButtonPressed() {
        DataReceiver.downloadBgm();
    }
}
