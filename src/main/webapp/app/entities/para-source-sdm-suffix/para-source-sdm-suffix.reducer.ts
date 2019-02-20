import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParaSourceSdmSuffix, defaultValue } from 'app/shared/model/para-source-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_PARASOURCES: 'paraSource/SEARCH_PARASOURCES',
  FETCH_PARASOURCE_LIST: 'paraSource/FETCH_PARASOURCE_LIST',
  FETCH_PARASOURCE: 'paraSource/FETCH_PARASOURCE',
  CREATE_PARASOURCE: 'paraSource/CREATE_PARASOURCE',
  UPDATE_PARASOURCE: 'paraSource/UPDATE_PARASOURCE',
  DELETE_PARASOURCE: 'paraSource/DELETE_PARASOURCE',
  SET_BLOB: 'paraSource/SET_BLOB',
  RESET: 'paraSource/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParaSourceSdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ParaSourceSdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ParaSourceSdmSuffixState = initialState, action): ParaSourceSdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARASOURCES):
    case REQUEST(ACTION_TYPES.FETCH_PARASOURCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARASOURCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARASOURCE):
    case REQUEST(ACTION_TYPES.UPDATE_PARASOURCE):
    case REQUEST(ACTION_TYPES.DELETE_PARASOURCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARASOURCES):
    case FAILURE(ACTION_TYPES.FETCH_PARASOURCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARASOURCE):
    case FAILURE(ACTION_TYPES.CREATE_PARASOURCE):
    case FAILURE(ACTION_TYPES.UPDATE_PARASOURCE):
    case FAILURE(ACTION_TYPES.DELETE_PARASOURCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARASOURCES):
    case SUCCESS(ACTION_TYPES.FETCH_PARASOURCE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARASOURCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARASOURCE):
    case SUCCESS(ACTION_TYPES.UPDATE_PARASOURCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARASOURCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/para-sources';
const apiSearchUrl = 'api/_search/para-sources';

// Actions

export const getSearchEntities: ICrudSearchAction<IParaSourceSdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PARASOURCES,
  payload: axios.get<IParaSourceSdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IParaSourceSdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PARASOURCE_LIST,
    payload: axios.get<IParaSourceSdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IParaSourceSdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARASOURCE,
    payload: axios.get<IParaSourceSdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IParaSourceSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARASOURCE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IParaSourceSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARASOURCE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParaSourceSdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARASOURCE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
