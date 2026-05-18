import { useState } from 'react';
import TopPageTab from '../components/TopPageTab';
import { TabConfig } from '../types/tabConfig';
import Logout from '../common-fragment-page/Logout';

type TabKey = 'check-now-material' | 'check-achievement' | 'new-schedule' | 'change-schedule' | 'change-calendar' | 'change-user' | 'logout';

const ManagerPage = () => {
  const [activeTab, setActiveTab] = useState<TabKey>('check-now-material');

  const tabData: TabConfig<TabKey>[] = [
    {
      id: 'check-now-material',
      label: '流動確認',
      content: <p>現在の流動状況確認画面</p>,
    },
    {
      id: 'check-achievement',
      label: '実績確認',
      content: <p>既存の実績を確認する</p>,
    },
    {
      id: 'new-schedule',
      label: '新規計画',
      content: <p>新規で計画を作成する</p>,
    },
    {
      id: 'change-schedule',
      label: '計画修正',
      content: <p>既存の計画を修正する</p>,
    },
    {
      id: 'change-calendar',
      label: '日程修正',
      content: <p>操業日程を修正する</p>,
    },
    {
      id: 'change-user',
      label: 'ユーザー修正',
      content: <p>このユーザー情報を修正する(passwordのみ)</p>,
    },
    {
      id: 'logout',
      label: 'ログアウト',
      content: <Logout></Logout>,
    }
  ];

  return (
    <TopPageTab activeTab={activeTab} setActiveTab={setActiveTab} tabData={tabData}></TopPageTab>
  );
};

export default ManagerPage;