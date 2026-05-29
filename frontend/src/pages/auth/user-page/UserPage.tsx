import { useState } from 'react';
import TopPageTab from '../components/TopPageTab';
import { TabConfig } from '../types/tabConfig';
import Logout from '../common-fragment-page/Logout';
import CorrectPassword from '../common-fragment-page/CorrectPassowrd';

type TabKey =
  | "check-now-material"
  | "achievement-registration"
  | "change-achievement"
  | "check-achievement"
  | "calendar-management"
  | "change-user"
  | "logout";

const UserPage = () => {
  const [activeTab, setActiveTab] = useState<TabKey>('check-now-material');

  const tabData: TabConfig<TabKey>[] = [
    {
      id: "check-now-material",
      label: "流動確認",
      content: <p>現在の流動状況確認画面</p>,
    },
    {
      id: "achievement-registration",
      label: "実績登録",
      content: <p>新規で実績を登録する</p>,
    },
    {
      id: "change-achievement",
      label: "実績修正",
      content: <p>既存の実績を修正する</p>,
    },
    {
      id: "check-achievement",
      label: "実績確認",
      content: <p>既存の実績を確認する</p>,
    },
    {
      id: "calendar-management",
      label: "日程修正",
      content: <p>操業日程の作成・修正・履歴確認</p>,
    },
    {
      id: "change-user",
      label: "ユーザー修正",
      content: <CorrectPassword></CorrectPassword>,
    },
    {
      id: "logout",
      label: "ログアウト",
      content: <Logout></Logout>,
    },
  ];

  return (
    <TopPageTab activeTab={activeTab} setActiveTab={setActiveTab} tabData={tabData}></TopPageTab>
  );
};

export default UserPage;